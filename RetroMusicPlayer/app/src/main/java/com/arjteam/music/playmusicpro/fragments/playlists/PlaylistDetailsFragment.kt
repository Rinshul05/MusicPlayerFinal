package com.arjteam.music.playmusicpro.fragments.playlists

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.observe
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arjteam.music.playmusicpro.R
import com.arjteam.music.playmusicpro.adapter.song.ShuffleButtonSongAdapter
import com.arjteam.music.playmusicpro.db.PlaylistWithSongs
import com.arjteam.music.playmusicpro.db.toSongs
import com.arjteam.music.playmusicpro.extensions.dipToPix
import com.arjteam.music.playmusicpro.extensions.resolveColor
import com.arjteam.music.playmusicpro.fragments.base.AbsMainActivityFragment
import com.arjteam.music.playmusicpro.helper.menu.PlaylistMenuHelper
import com.arjteam.music.playmusicpro.model.Song
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_playlist_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PlaylistDetailsFragment : AbsMainActivityFragment(R.layout.fragment_playlist_detail) {
    private val arguments by navArgs<PlaylistDetailsFragmentArgs>()
    private val viewModel by viewModel<PlaylistDetailsViewModel> {
        parametersOf(arguments.extraPlaylist)
    }

    private lateinit var playlist: PlaylistWithSongs
    private lateinit var playlistSongAdapter: ShuffleButtonSongAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.fragment_container
            duration = 300L
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().resolveColor(R.attr.colorSurface))
            setPathMotion(MaterialArcMotion())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        mainActivity.setBottomBarVisibility(false)
        mainActivity.addMusicServiceEventListener(viewModel)
        mainActivity.setSupportActionBar(toolbar)
        ViewCompat.setTransitionName(container, "playlist")
        playlist = arguments.extraPlaylist
        toolbar.title = playlist.playlistEntity.playlistName
        setUpRecyclerView()
        viewModel.getSongs().observe(viewLifecycleOwner, { songs(it.toSongs()) })
    }

    private fun setUpRecyclerView() {
        playlistSongAdapter = ShuffleButtonSongAdapter(
            requireActivity(),
            ArrayList(),
            R.layout.item_list,
            null,
        )
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = playlistSongAdapter
        }
        playlistSongAdapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkIsEmpty()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_playlist_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return PlaylistMenuHelper.handleMenuClick(requireActivity(), playlist, item)
    }

    private fun checkForPadding() {
        val height = dipToPix(52f)
        recyclerView.setPadding(0, 0, 0, height.toInt())
    }

    private fun checkIsEmpty() {
        checkForPadding()
        empty.isVisible = playlistSongAdapter.itemCount == 0
        emptyText.isVisible = playlistSongAdapter.itemCount == 0
    }

    override fun onDestroy() {
        recyclerView?.itemAnimator = null
        recyclerView?.adapter = null
        super.onDestroy()
    }

    private fun showEmptyView() {
        empty.visibility = View.VISIBLE
        emptyText.visibility = View.VISIBLE
    }

    fun songs(songs: List<Song>) {
        progressIndicator.hide()
        if (songs.isNotEmpty()) {
            playlistSongAdapter.swapDataSet(songs)
        } else {
            showEmptyView()
        }
    }
}


