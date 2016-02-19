# BoxFactory

A small distro intended for assembling minimal docker images.  The idea
is that your docker image will contain only what is necessary and
nothing more.

I was appalled that the golang-runtime docker images were hundreds of
MB, for what should be essentially glibc and a mostly statically
linked binary.  The available alternatives were basically
[use `scratch` and do it yourself](http://blog.xebia.com/2014/07/04/create-the-smallest-possible-docker-container/)
or [the busybox image](https://registry.hub.docker.com/_/busybox/),
both of which are "too minimal" and require additional work to build
on.  "BoxFactory" is my solution to this. `boxfactory-base` is about
20MB and includes a busybox shell environment, glibc, and just enough
to be able to install additional packages.  No kernel, no udev, no
sshd.  When you need more, it's just an `opkg install` away.

*This is a trivial use of yocto/openembedded to build docker filesystem
images, and any credit should go to those projects.*

## To Use:

```Dockerfile
FROM anguslees/boxfactory-base
RUN opkg update && opkg install apache2
EXPOSE 80
CMD ["/usr/sbin/apache2"]
```

### Images Available:

* `anguslees/boxfactory-base` - minimal image
* `anguslees/boxfactory-python2` - base + python2.7 + pip + virtualenv
* `anguslees/boxfactory-python3` - base + python3.3 + pip + virtualenv
* `anguslees/boxfactory-perl` - base + perl
* `anguslees/boxfactory-ruby` - base + ruby

If you think you have another combination that would be useful,
[just ask](mailto:gus@inodes.org) or send a github pull request.

### Packages Available:

To see the latest list of packages available:

```ShellSession
docker run --rm -ti boxfactory-base
opkg update
opkg list
```

If there's a particular piece of software you were surprised was
missing from that list, let me know.

## To Build:

*Note this is not necessary to use boxfactory and will hasten the heat
death of the universe.*


```ShellSession
git clone https://github.com/anguslees/boxfactory.git
cd boxfactory
git submodule init
cd oe/build
source oe-build-env
```

At this point you can run `bitbake docker-image-minimal` to build the
original root filesystem used in `boxfactory-base`, or `bitbake <package
name>` to build a particular package.  If you have lots of disk and
cpu time, `bitbake -k world` will attempt to build every package
possible (some will fail).

## Bugs/Questions:

Email gus@inodes.org, or file issues and pull-requests against
[the github project](https://github.com/anguslees/boxfactory)
