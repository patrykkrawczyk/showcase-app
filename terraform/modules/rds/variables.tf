variable "name" {
  description = "Name of the db"
}

variable "environment" {
  description = "The name of the environment"
}

variable "instance_class" {
  description = "The instance class to be used"
}

variable "private_subnet_ids" {
}

variable "public_subnet_ids" {

}

variable "vpc_id" {
}

variable "ecs_security_group_id" {
}
