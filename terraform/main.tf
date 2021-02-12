terraform {
  backend "s3" {
    bucket = "loki-bay-infrastructure-4659b377-379f-b298-ccc1-04e0dfa7fe9a"
    key    = "terraform/infrastructure/terraform.tfstate"
    region = "eu-west-1"
  }
}

provider "aws" {
  region = "eu-west-1"
}

module "ecs" {
  source = "./modules/ecs"

  environment          = var.environment
  cluster              = var.cluster
  db_name              = var.db_name
  cloudwatch_prefix    = var.environment
  vpc_cidr             = var.vpc_cidr
  public_subnet_cidrs  = var.public_subnet_cidrs
  private_subnet_cidrs = var.private_subnet_cidrs
  availability_zones   = var.availability_zones
  max_size             = var.max_size
  min_size             = var.min_size
  desired_capacity     = var.desired_capacity
  instance_type        = var.instance_type
  ecs_aws_ami          = var.ecs_aws_ami
}
