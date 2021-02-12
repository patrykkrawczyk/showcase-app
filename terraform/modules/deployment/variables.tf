variable "environment" {
  type = string
}

variable "service_name" {
  type = string
}

variable "ecs_instance_security_group_id" {
  type = string
}

variable "public_subnet_ids" {
  type = list(string)
}

variable "health_check_path" {
  type = string
}

variable "vpc_id" {
  type = string
}

variable "cluster" {
  type = string
}
