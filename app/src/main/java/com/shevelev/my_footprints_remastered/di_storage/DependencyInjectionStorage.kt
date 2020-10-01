package com.shevelev.my_footprints_remastered.di_storage

import android.app.Application
import com.shevelev.my_footprints_remastered.application.di.AppComponent
import com.shevelev.my_footprints_remastered.application.di.AppModule
import com.shevelev.my_footprints_remastered.application.di.DaggerAppComponent
import com.shevelev.my_footprints_remastered.ui.di.UIComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.di.MainActivityComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.di.CreateFootprintFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.di.SetLocationFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_crop_photo.di.CropPhotoFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_edit_photo.di.EditPhotoFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.di.GalleryGridFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_one.di.GalleryOneFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.di.SelectPhotoFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.di.SetLocationMapFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_stub.di.SetLocationStubFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.di.TitleFragmentComponent
import com.shevelev.my_footprints_remastered.ui.shared.dialogs.map.MapDialogComponent
import com.shevelev.my_footprints_remastered.utils.id_hash.IdUtil
import kotlin.reflect.KClass

/** Storage for Dagger components on application level  */
class DependencyInjectionStorage(private val app: Application) {

    private val components = mutableMapOf<KClass<*>, MutableMap<String, Any>>()

    inline fun <reified T> get(key: String, vararg args: Any?): T = getComponent(key, T::class, args)

    inline fun <reified T> release(key: String) = releaseComponent(key, T::class)

    @Suppress("UNCHECKED_CAST")
    fun <T> getComponent(key: String, type: KClass<*>, args: Array<out Any?>): T {
        val componentsSet = components[type]

        if(componentsSet == null) {
            val component = provideComponent<T>(type, args)
            components[type] = mutableMapOf(key to component as Any)
            return component
        } else {
            var component = componentsSet[key]
            if(component != null) {
                return component as T
            }

            component = provideComponent<T>(type, args)
            componentsSet[key] = component as Any

            return component
        }
    }

    fun releaseComponent(key: String, type: KClass<*>) {
        val componentsSet = components[type] ?: return

        componentsSet.remove(key)

        if(componentsSet.isEmpty()) {
            components.remove(type)
        }
    }

    inline fun <reified T> getBase(): T = getBaseComponent(T::class)

    @Suppress("UNCHECKED_CAST")
    fun <T> getBaseComponent(type: KClass<*>): T {
        val componentsSet = components[type]

        return if(componentsSet != null) {
            componentsSet.entries.first().value as T
        } else {
            val component = provideComponent<T>(type, arrayOfNulls(0))
            components[type] = mutableMapOf(IdUtil.generateStringId() to component as Any)

            component
        }
    }

    @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST", "UNUSED_PARAMETER")
    private fun <T> provideComponent(type: KClass<*>, args: Array<out Any?>): T {
        @Suppress("EXPERIMENTAL_API_USAGE")
        return when (type) {
            AppComponent::class -> DaggerAppComponent.builder().appModule(AppModule(app)).build()

            UIComponent::class -> getBase<AppComponent>().ui.build()

            MainActivityComponent::class -> getBase<UIComponent>().mainActivity.build()

            TitleFragmentComponent::class -> getBase<MainActivityComponent>().titleFragment.build()

            CreateFootprintFragmentComponent::class -> getBase<MainActivityComponent>().createFootprintFragment.build()

            SelectPhotoFragmentComponent::class -> getBase<CreateFootprintFragmentComponent>().selectPhotoFragment.build()

            CropPhotoFragmentComponent::class -> getBase<CreateFootprintFragmentComponent>().cropPhotoFragment.build()

            EditPhotoFragmentComponent::class -> getBase<CreateFootprintFragmentComponent>().editPhotoFragment.build()

            SetLocationFragmentComponent::class -> getBase<CreateFootprintFragmentComponent>().setLocationFragment.build()

            SetLocationStubFragmentComponent::class -> getBase<CreateFootprintFragmentComponent>().setLocationStubFragment.build()

            SetLocationMapFragmentComponent::class -> getBase<CreateFootprintFragmentComponent>().setLocationMapFragment.build()

            GalleryGridFragmentComponent::class -> getBase<MainActivityComponent>().galleryGridFragment.build()

            GalleryOneFragmentComponent::class -> getBase<MainActivityComponent>().galleryOneFragment.build()

            MapDialogComponent::class -> getBase<MainActivityComponent>().mapDialog.build()

            else -> throw UnsupportedOperationException("This component is not supported: ${type.simpleName}")
        } as T
    }
}