package com.example.filmly.ui.cardDetail


import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.filmly.R
import com.example.filmly.adapters.CardDetailListsAdapter
import com.example.filmly.adapters.CardDetailProvidersAdapter
import com.example.filmly.data.model.Card
import com.example.filmly.data.model.CardDetail
import com.example.filmly.data.model.FastBlur
import com.example.filmly.repository.ServicesRepository
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_card_detail.view.*


class CardDetailFragment : Fragment() {
    private lateinit var repository: ServicesRepository
    private val viewModel: CardDetailViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return CardDetailViewModel(repository) as T
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card_detail, container, false)

        repository = ServicesRepository.getInstance(requireContext())

        view.app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val offsetAlpha: Float = appBarLayout.y / view.app_bar.getTotalScrollRange()
            view.iv_cardDetailImageBlur.setAlpha(offsetAlpha * -1)
        })

        (view.toolbar.navigationIcon as LayerDrawable).findDrawableByLayerId(R.id.seta_cardDetail).setTint(view.resources.getColor(R.color.white))
        view.toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

        val circularProgressDrawable = CircularProgressDrawable(view.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()



        view.rc_serie_seasons.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        view.rc_serie_seasons.setHasFixedSize(true)

        val detail = arguments?.getSerializable("detail") as CardDetail

      
        controlFavoriteState(detail.card, view)

        view.tv_titleDetail.text = ""
        view.tv_sinopseCardDetail.text = ""


        //Serie Configuration START ->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        when(detail.card.type){
             "tv" -> {
                 viewModel.getProvidersDetail(detail.card.id!!)


                 viewModel.tvProvidersLive.observe(viewLifecycleOwner){
                     view.rc_serie_seasons.apply {
                         adapter = it.seasons?.let { it1 -> CardDetailListsAdapter(it1) }
                         layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
                         setHasFixedSize(true)
                     }

                     view.rc_serie_watch.apply {
                         view.tv_titleDetail.text = it.name
                         view.tv_sinopseCardDetail.text = it.overview

                         if (it.watch?.results?.BR != null) {
                         adapter = CardDetailProvidersAdapter(it.watch.results.BR.flatrate!!.plus(it.watch.results.BR.buy!!).plus(it.watch.results.BR.rent!!).plus(it.watch.results.BR.ads!!).distinct())
                         layoutManager = LinearLayoutManager(view.context)
                         setHasFixedSize(true)
                        }else{
                            view.tv_title_provider.visibility = View.GONE
                         }
                     }
                 }

            }
            "movie" -> {
                view.tv_title_rc.visibility = View.GONE
                viewModel.getProvidersMovieDetail(detail.card.id!!)


                viewModel.movieProvidersLive.observe(viewLifecycleOwner) {
                    view.tv_titleDetail.text = it.title
                    view.tv_sinopseCardDetail.text = it.overview

                    view.rc_serie_watch.apply {
                        if (it.watch?.results?.BR != null) {
                            adapter = CardDetailProvidersAdapter(
                                it.watch.results.BR.flatrate!!.plus(it.watch.results.BR.buy!!)
                                    .plus(it.watch.results.BR.rent!!).plus(it.watch.results.BR.ads!!).distinct()
                            )
                            layoutManager = LinearLayoutManager(view.context)
                            setHasFixedSize(true)
                        } else {
                            view.tv_title_provider.visibility = View.GONE
                        }
                    }

                }
            }
            "person" ->{
                view.tv_title_rc.visibility = View.GONE
                view.tv_title_provider.visibility = View.GONE

                view.tv_sinopseCardDetail.text = detail.card.descricao
                view.tv_titleDetail.text = detail.card.name

            }



        }

        //END ->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


        Glide.with(view).asBitmap()
            .load("https://image.tmdb.org/t/p/w500${detail.card.image}")
            .placeholder(circularProgressDrawable)
            .error(R.drawable.placeholder)
            .fallback(R.drawable.placeholder)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    view.iv_cardDetailImage.setImageBitmap(resource)
                    view.iv_cardDetailImageBlur.setImageBitmap(FastBlur.doBlur(resource, 10, false))
                }
                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })




        view.btn_addToLists.setOnClickListener {
            viewModel.isFavorited.value?.let { isFavorited ->
                if (!isFavorited) {
                    viewModel.addCard(detail.card)
                    viewModel.isFavorited()
                    Toast.makeText(context, "Adicionado", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.deleteCard(detail.card)
                    viewModel.isNotFavorited()
                    Toast.makeText(context, "Item removido", Toast.LENGTH_SHORT).show()
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            view.tv_sinopseCardDetail.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }

        return view
    }

    fun controlFavoriteState(card: Card, view: View) {
        viewModel.isFavorited.observe(viewLifecycleOwner) { isFavorited ->
            Log.i("LiveData", "$isFavorited")
            view.btn_addToLists.isSelected = isFavorited
        }

        viewModel.checkCardisFavorited(card)
    }

}