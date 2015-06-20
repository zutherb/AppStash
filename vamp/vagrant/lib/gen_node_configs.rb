# -*- mode: ruby -*-
# vi: set ft=ruby :

def gen_node_configs(cluster_yml)
  master_n = cluster_yml['master_n']
  master_mem = cluster_yml['master_mem']
  master_cpus = cluster_yml['master_cpus']
  slave_n = cluster_yml['slave_n']
  slave_mem = cluster_yml['slave_mem']
  slave_cpus = cluster_yml['slave_cpus']
  master_ipbase = cluster_yml['master_ipbase']
  slave_ipbase = cluster_yml['slave_ipbase']
  master_instance_type = cluster_yml['master_instance_type']
  slave_instance_type = cluster_yml['slave_instance_type']
  master_droplet_size = cluster_yml['master_droplet_size']
  slave_droplet_size = cluster_yml['slave_droplet_size']

  master_infos = (1..master_n).map do |i|
                   { :hostname => "master#{i}",
                     :ip => master_ipbase + "#{10+i}",
                     :mem => master_mem,
                     :cpus => master_cpus,
                     :instance_type => master_instance_type,
                     :size => master_droplet_size
                   }
                 end
  slave_infos = (1..slave_n).map do |i|
                   { :hostname => "slave#{i}",
                     :ip => slave_ipbase + "#{10+i}",
                     :mem => slave_mem,
                     :cpus => slave_cpus,
                     :instance_type => slave_instance_type,
                     :size => slave_droplet_size
                   }
                 end

  return { :master => master_infos, :slave=>slave_infos }
end
