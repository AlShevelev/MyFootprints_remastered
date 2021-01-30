package com.shevelev.my_footprints_remastered.image_type_detector.dagger

import com.shevelev.my_footprints_remastered.image_type_detector.ImageTypeDetector
import com.shevelev.my_footprints_remastered.image_type_detector.ImageTypeDetectorImpl
import com.shevelev.my_footprints_remastered.image_type_detector.signatures.ImageSignatureFactory
import dagger.Module
import dagger.Provides

@Module
class ImageTypeDetectorModule() {
    @Provides
    fun provideImageTypeDetector(): ImageTypeDetector =
        ImageTypeDetectorImpl(
            listOf(
                ImageSignatureFactory.getJpegSignature(),
                ImageSignatureFactory.getPngSignature()))
}