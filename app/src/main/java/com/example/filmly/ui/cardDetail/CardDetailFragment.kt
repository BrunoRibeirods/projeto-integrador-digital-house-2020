package com.example.filmly.ui.cardDetail


import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.filmly.R
import com.example.filmly.adapters.CardDetailListsAdapter
import com.example.filmly.adapters.CardDetailProvidersAdapter
import com.example.filmly.adapters.KnownForAdapter
import com.example.filmly.data.model.Actor
import com.example.filmly.data.model.Card
import com.example.filmly.data.model.CardDetail
import com.example.filmly.data.model.FastBlur
import com.example.filmly.repository.ServicesRepository
import com.example.filmly.utils.CardDetailNavigation
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_card_detail.view.*


class CardDetailFragment : Fragment(), CardDetailListsAdapter.OnClickSeasonListener {
    private lateinit var repository: ServicesRepository
    lateinit var season: CardDetail
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
        season = detail

      
        controlFavoriteState(detail.card, view)

        view.tv_titleDetail.text = ""
        view.tv_sinopseCardDetail.text = ""


        //Serie Configuration START ->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        if(isOnline(view.context)) {
            when (detail.card.type) {
                "tv" -> {
                    viewModel.getProvidersDetail(detail.card.id!!)


                    viewModel.tvProvidersLive.observe(viewLifecycleOwner) {
                        view.rc_serie_seasons.apply {
                            adapter = it.seasons?.let { it1 ->
                                CardDetailListsAdapter(
                                    it1,
                                    this@CardDetailFragment
                                )
                            }
                            layoutManager = LinearLayoutManager(
                                view.context,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                            setHasFixedSize(true)
                        }

                        view.rc_serie_watch.apply {
                            view.tv_titleDetail.text = it.name
                            view.tv_sinopseCardDetail.text = it.overview
                            if (TextUtils.isEmpty(view.tv_sinopseCardDetail.text)) view.tv_sinopseCardDetail.text =
                                "Descrição não disponivel no momento."

                            if (it.watch?.results?.BR != null) {
                                adapter = CardDetailProvidersAdapter(
                                    it.watch.results.BR.flatrate!!.plus(it.watch.results.BR.buy!!)
                                        .plus(it.watch.results.BR.rent!!)
                                        .plus(it.watch.results.BR.ads!!).distinct()
                                )
                                layoutManager = LinearLayoutManager(view.context)
                                setHasFixedSize(true)
                            } else {
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
                        if(TextUtils.isEmpty(view.tv_sinopseCardDetail.text)) view.tv_sinopseCardDetail.text = "Descrição não disponivel no momento."

                        view.rc_serie_watch.apply {
                            if (it.watch?.results?.BR != null) {
                                adapter = CardDetailProvidersAdapter(
                                    it.watch.results.BR.flatrate!!.plus(it.watch.results.BR.buy!!)
                                        .plus(it.watch.results.BR.rent!!)
                                        .plus(it.watch.results.BR.ads!!).distinct()
                                )
                                layoutManager = LinearLayoutManager(view.context)
                                setHasFixedSize(true)
                            } else {
                                view.tv_title_provider.visibility = View.GONE
                            }
                        }

                    }
                }
                "person" -> {
                    view.tv_title_provider.visibility = View.GONE

                    view.tv_title_rc.text = "Conhecido por:"
                    view.tv_title_rc.setTextColor(resources.getColor(R.color.yellow))

                    view.tv_titleDetail.text = detail.card.name

                    val actor = detail.card as Actor?

                    actor?.id?.let { viewModel.getActorDetail(it).observe(viewLifecycleOwner) {
                        view.tv_sinopseCardDetail.text = it.biography
                    } }

                    actor?.known_for?.let { view.rc_serie_seasons.apply {
                        adapter = KnownForAdapter(it, CardDetailNavigation { detail ->
                            val action = CardDetailFragmentDirections.actionCardDetailFragmentSelf(detail)
                            findNavController().navigate(action)
                        })
                        layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
                        setHasFixedSize(true)
                    } }

                }


            }
        }else{
            view.tv_titleDetail.text = detail.card.name.toString()
            view.tv_sinopseCardDetail.text = detail.card.descricao.toString()
            view.tv_title_rc.visibility = View.GONE
            view.tv_title_provider.visibility = View.GONE
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

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)


        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }

        return false
    }


    override fun onClickSeason(position: Int) {



        viewModel.tvProvidersLive.observe(viewLifecycleOwner){ tvDetailsResults ->
            tvDetailsResults.seasons.let {
            val bundle = bundleOf("id" to season.card.id,
                    "season_number" to it!![position].season_number,
                    "name" to season.card.name
                )

                findNavController().navigate(R.id.action_cardDetailFragment_to_seasonDetailFragment, bundle)
            }
        }


    }

}