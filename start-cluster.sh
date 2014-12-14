#!/bin/sh
INSTALL_COMPLETE_FILE="~/appstash/.appstash-setup-complete"
BREW_DIRECTORY="/usr/local/Cellar"

function install_homebrew(){
    echo "************************************"
    echo "Install Homebrew                    "
    echo "************************************"
    ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
}

function install_dependencies(){
    echo "************************************"
    echo "Install Appstash Dependencies       "
    echo "************************************"
    brew install git
    brew tap caskroom/cask
    brew install brew-cask
    brew cask install virtualbox
    brew cask install vagrant
    brew cask install vagrant-manager
    brew install ansible
    vagrant plugin install vagrant-cachier
    vagrant plugin install vagrant-hostsupdater
}

function clone_repo(){
    echo "************************************"
    echo "Cloning Appstash                    "
    echo "************************************"
    cd ~
    git clone git@github.com:zutherb/AppStash.git appstash
}

if [-f $BREW_DIRECTORY]
then
    echo "************************************"
    echo "Homebrew is installed               "
    echo "************************************"
else
    install_homebrew
fi

if [-f $INSTALL_COMPLETE_FILE]
then
    echo "************************************"
    echo "Appstash Dependencies are installed "
    echo "************************************"
else
    install_dependencies
    clone_repo
    touch $INSTALL_COMPLETE_FILE
fi
echo "************************************"
echo "Start Appstash Cluster              "
echo "************************************"
cd ~/appstash/vagrant
vagrant up