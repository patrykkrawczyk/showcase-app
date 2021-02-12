module "network" {
  source               = "../network"
  environment          = var.environment
  vpc_cidr             = var.vpc_cidr
  public_subnet_cidrs  = var.public_subnet_cidrs
  private_subnet_cidrs = var.private_subnet_cidrs
  availability_zones   = var.availability_zones
}

module "ecs_instances" {
  source = "../ecs_instances"

  environment             = var.environment
  cluster                 = var.cluster
  instance_group          = var.instance_group
  public_subnet_ids       = module.network.public_subnet_ids
  aws_ami                 = var.ecs_aws_ami
  instance_type           = var.instance_type
  max_size                = var.max_size
  min_size                = var.min_size
  desired_capacity        = var.desired_capacity
  vpc_id                  = module.network.vpc_id
  iam_instance_profile_id = aws_iam_instance_profile.ecs.id
  load_balancers          = var.load_balancers
  custom_userdata         = var.custom_userdata
  cloudwatch_prefix       = var.cloudwatch_prefix
}

module "ecs_events" {
  source = "../ecs_events"

  environment             = var.environment
  cluster                 = var.cluster
}

resource "aws_ecs_cluster" "cluster" {
  name = var.cluster
}

module "rds" {
  source = "../rds"

  environment           = var.environment
  name                  = var.db_name
  instance_class        = "db.t2.micro"
  private_subnet_ids    = module.network.private_subnet_ids
  public_subnet_ids     = module.network.public_subnet_ids
  vpc_id                = module.network.vpc_id
  ecs_security_group_id = module.ecs_instances.ecs_instance_security_group_id
}


module "ecs_roles" {
  source = "../ecs_roles"

  environment             = var.environment
  cluster                 = var.cluster
}

module "deployment" {
  source = "../deployment"

  environment = var.environment
  cluster = var.cluster
  service_name = "showcase-service"
  health_check_path = "/actuator/health"
  ecs_instance_security_group_id = module.ecs_instances.ecs_instance_security_group_id
  public_subnet_ids = module.network.public_subnet_ids
  vpc_id = module.network.vpc_id
}
