LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE        := NeoVisionCamera
LOCAL_MODULE_TAGS   := optional
LOCAL_MODULE_CLASS  := APPS
LOCAL_MODULE_OWNER  := nubia
LOCAL_MODULE_SUFFIX := .apk
LOCAL_SRC_FILES     := NeoVisionCamera.apk
LOCAL_CERTIFICATE   := shared
include $(BUILD_PREBUILT)


include $(CLEAR_VARS)

LOCAL_SRC_FILES := \
    Fence.cpp

LOCAL_SHARED_LIBRARIES := libbinder libui

LOCAL_MODULE := libnubia_symbols
LOCAL_MODULE_TAGS := optional
LOCAL_MODULE_CLASS := SHARED_LIBRARIES

include $(BUILD_SHARED_LIBRARY)
