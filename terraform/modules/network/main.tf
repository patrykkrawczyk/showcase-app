module "vpc" {
  source = "../vpc"

  cidr        = var.vpc_cidr
  environment = var.environment
}

module "private_subnet" {
  source = "../subnet"

  name               = "${var.environment}_private_subnet"
  environment        = var.environment
  vpc_id             = module.vpc.id
  cidrs              = var.private_subnet_cidrs
  availability_zones = var.availability_zones
  map_public_ip_on_launch = false
}

module "public_subnet" {
  source = "../subnet"

  name               = "${var.environment}_public_subnet"
  environment        = var.environment
  vpc_id             = module.vpc.id
  cidrs              = var.public_subnet_cidrs
  availability_zones = var.availability_zones
  map_public_ip_on_launch = true
}

resource "aws_route" "public_igw_route" {
  count                  = length(var.public_subnet_cidrs)
  route_table_id         = element(module.public_subnet.route_table_ids, count.index)
  gateway_id             = module.vpc.igw
  destination_cidr_block = var.destination_cidr_block
}
