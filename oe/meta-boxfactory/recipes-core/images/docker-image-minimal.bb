SUMMARY = "A minimal docker image that supports installing additional packages"

IMAGE_FEATURES += "package-management"
IMAGE_INSTALL = "base-files busybox ${CORE_IMAGE_EXTRA_INSTALL}"

LICENSE = "MIT"

USE_DEPMOD = "0"
IMAGE_LINGUAS = "en-us"

inherit core-image
