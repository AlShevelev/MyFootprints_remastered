package com.shevelev.my_footprints_remastered.ui.shared.dialogs.map

import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import dagger.Subcomponent

@Subcomponent
@FragmentScope
interface MapDialogComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): MapDialogComponent
    }

    fun inject(dialog: MapDialog)
}