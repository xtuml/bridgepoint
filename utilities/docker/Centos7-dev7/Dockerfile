FROM centos/devtoolset-7-toolchain-centos7

USER 0
RUN yum update -y && \
    yum install -y sudo \
        vim \
        tree \
	wget \
	git \
	zlib-devel \
	libssl-devel \
	bzip2-devel \
	readline-devel \
	openssl-devel \
	java-1.8.0-openjdk

COPY install-bridgepoint.sh ${HOME}/install-bridgepoint.sh
RUN /bin/bash ${HOME}/install-bridgepoint.sh

#COPY entrypoint.sh /run/entrypoint.sh
WORKDIR /development
CMD ["/opt/bridgepoint/latest/bridgepoint"]
