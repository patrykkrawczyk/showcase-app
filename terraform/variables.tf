variable "vpc_cidr" {}
variable "environment" {}
variable "cluster" {}
variable "db_name" {}
variable "max_size" {}
variable "min_size" {}
variable "desired_capacity" {}
variable "instance_type" {}
variable "ecs_aws_ami" {}

variable "public_subnet_cidrs" {
  type = list
}

variable "private_subnet_cidrs" {
  type = list
}

variable "availability_zones" {
  type = list
}
