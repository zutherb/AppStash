vagrant-puppet-mesosphere
====================
Create your [Mesosphere](http://mesosphere.com) stack with [Vagrant](http://www.vagrantup.com) and [Puppet](http://puppetlabs.com/) (Virtualbox / AWS / Digital Ocean are supported providers).

This creates a Mesos cluster in which [Marathon](https://github.com/mesosphere/marathon) framework and [Consul](https://github.com/hashicorp/consul) (Optional, used for service discovery) are running.  This means you can build your own __Mesos+Marathon+Docker__ PaaS with `vagrant up`!  _If you want to deploy docker containers, please refer to the chapter "Deploy Docker Container with Marathon" in [this blog entry](http://frankhinek.com/deploy-docker-containers-on-mesos-0-20/)._

* Using VirtualBox
	* [Mesos Standalone on VirtualBox](#svb)
	* [Mesos Cluster on VirtualBox](#clvb)

Prerequisites
----
* vagrant 1.6.5+: <http://www.vagrantup.com/>
* VirtualBox: <https://www.virtualbox.org/> (not required if you use ec2/DigitalOcean.)
* vagrant plugins
    * [vagrant-hosts](https://github.com/adrienthebo/vagrant-hosts)
        `$ vagrant plugin install vagrant-hosts`
    * [vagrant-cachier](https://github.com/fgrehm/vagrant-cachier)
        `$ vagrant plugin install vagrant-cachier`
    * [vagrant-aws](https://github.com/mitchellh/vagrant-aws) (only if you use ec2.)
        `$ vagrant plugin install vagrant-aws`
    * [vagrant-digitalocean](https://github.com/smdahlen/vagrant-digitalocean) (only if you use DigitalOcean.)
        `$ vagrant plugin install vagrant-digitalocean`

Clone this repository and update git submodules:

```shell
$ git clone https://github.com/tayzlor/vagrant-puppet-mesosphere.git
$ cd vagrant-puppet-mesosphere
$ git submodule init
$ git submodule update
```

<a name="svb"></a>
Mesos Standalone on VirtualBox
----

Includes -

* Docker (1.4.0)
* Mesos master (x1)
* Mesos slave (x1)
* Marathon (0.7.5)
* Zookeeper
* Consul, running in server mode (Optional - configure via ```consul_enable:``` param in ```hieradata/common.yml```  )

```shell
$ cd standalone
$ vagrant up
```

After box is up, you can see services running at:

* Mesos web UI on: <http://mesos:5050>
* [Marathon](https://github.com/mesosphere/marathon) web UI on: <http://mesos:8080>
* [Consul](https://github.com/hashicorp/consul) web UI on: <http://mesos:8500>

<a name="clvb"></a>
Mesos Cluster on VirtualBox
----
### Cluster Configuration
Cluster configuration is defined at `cluster/cluster.yml`.  You can edit the file to configure cluster settings.

```yaml
# Mesos cluster configurations
# The numbers of servers
##############################
master_n: 1      # hostname will be master1,master2,…
slave_n : 1      # hostname will be slave1,slave2,…

# Memory and Cpus setting(only for virtualbox)
##########################################
master_mem : 256
master_cpus: 1
slave_mem  : 512
slave_cpus : 2

# private ip bases
# When ec2, this should be matched with
# private addresses defined by subnet_id below.
################################################
master_ipbase: "172.31.1."
slave_ipbase : "172.31.2."
```

### Node types

#### mesos-master
Includes -

* Docker (1.4.0)
* Mesos (running in master server mode)
* Marathon (0.7.5)
* Zookeeper
* Consul, running in server mode (Optional - configure via ```consul_enable:``` param in ```cluster.yml```)

#### mesos-slave
Includes -

* Docker (1.4.0)
* Mesos (running in slave server mode)
* Consul, running in agent mode (optional - configure via ```consul_enable:``` param in ```cluster.yml```)

### Launch Cluster
This may takes several minutes(10 to 20 min.).

```shell
$ cd cluster
$ vagrant up
```

At default setting, after all the boxes are up, you can see services running at:

* Mesos web UI on: <http://172.31.1.11:5050>
* [Marathon](https://github.com/mesosphere/marathon) web UI on: <http://172.31.3.11:8080>
* [Chronos](https://github.com/mesosphere/chronos) web UI on: <http://172.31.3.11:8081>

#### Destroy Cluster
this operations all VM instances forming the cluster.

```shell
$ cd cluster
$ vagrant destroy
```

#### Credits
Inspired by - [https://github.com/everpeace/vagrant-mesos](https://github.com/everpeace/vagrant-mesos) which is similar but uses Chef, and has a slightly different cluster layout / components.
[http://philzim.com/2014/11/12/service-discovery-orchestration-with-mesos-and-consul](http://philzim.com/2014/11/12/service-discovery-orchestration-with-mesos-and-consul)
