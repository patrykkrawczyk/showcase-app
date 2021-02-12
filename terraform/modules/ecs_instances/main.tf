resource "aws_security_group" "instance" {
  name        = join("_", [var.environment, var.cluster, var.instance_group])
  description = "Used in ${var.environment}"
  vpc_id      = var.vpc_id
}

resource "aws_security_group_rule" "outbound_internet_access" {
  type              = "egress"
  from_port         = 0
  to_port           = 0
  protocol          = "-1"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.instance.id
}

resource "aws_launch_configuration" "launch" {
  name_prefix          = "${join("_", [var.environment, var.cluster, var.instance_group])}_"
  image_id             = var.aws_ami
  instance_type        = var.instance_type
  security_groups      = [aws_security_group.instance.id]
  user_data            = data.template_file.user_data.rendered
  iam_instance_profile = var.iam_instance_profile_id
  associate_public_ip_address = true

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_autoscaling_group" "asg" {
  name                 = join("_", [var.environment, var.cluster, var.instance_group])
  max_size             = var.max_size
  min_size             = var.min_size
  desired_capacity     = var.desired_capacity
  force_delete         = true
  launch_configuration = aws_launch_configuration.launch.id
  vpc_zone_identifier  = var.public_subnet_ids
  load_balancers       = var.load_balancers
}

data "template_file" "user_data" {
  template = file("${path.module}/templates/user_data.sh")

  vars = {
    ecs_config        = var.ecs_config
    ecs_logging       = var.ecs_logging
    cluster_name      = var.cluster
    env_name          = var.environment
    custom_userdata   = var.custom_userdata
    cloudwatch_prefix = var.cloudwatch_prefix
  }
}
