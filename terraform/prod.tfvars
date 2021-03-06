vpc_cidr = "10.0.0.0/16"

environment = "prod"

cluster = "showcase"
db_name = "showcase"

public_subnet_cidrs = ["10.0.0.0/24", "10.0.1.0/24"]

private_subnet_cidrs = ["10.0.50.0/24", "10.0.51.0/24"]

availability_zones = ["eu-west-1a", "eu-west-1b"]

max_size = 1

min_size = 1

desired_capacity = 1

instance_type = "t2.micro"

ecs_aws_ami = "ami-02e8b3843287bc013"
