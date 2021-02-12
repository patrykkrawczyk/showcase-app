data "aws_ssm_parameter" "db_password" {
  name = "/showcase/db/password"
}

data "aws_ssm_parameter" "db_username" {
  name = "/showcase/db/username"
}

resource "aws_db_instance" "postgres" {
  identifier                = "${var.name}-${var.environment}"
  allocated_storage         = 20
  storage_type              = "gp2"
  engine                    = "postgres"
  engine_version            = "12.4"
  instance_class            = var.instance_class
  name                      = "postgres"
  username                  = data.aws_ssm_parameter.db_username.value
  password                  = data.aws_ssm_parameter.db_password.value
  publicly_accessible       = true
  security_group_names      = []
  vpc_security_group_ids    = ["${aws_security_group.rds.id}"]
  db_subnet_group_name      = aws_db_subnet_group.postgres.id
  multi_az                  = false
  backup_retention_period   = 7
  backup_window             = "05:20-05:50"
  maintenance_window        = "sun:04:00-sun:04:30"
  final_snapshot_identifier = "${var.name}-${var.environment}-final"
  skip_final_snapshot       = true
}

//resource "aws_db_subnet_group" "postgres" {
//  name        = "${var.name}-${var.environment}"
//  subnet_ids  = var.private_subnet_ids
//}

// TODO this is for exercise purposes only
resource "aws_db_subnet_group" "postgres" {
  name        = "${var.name}-${var.environment}"
  subnet_ids  = var.public_subnet_ids
}

resource "aws_security_group" "rds" {
  name   = "${var.name}-${var.environment}-rds"
  vpc_id = var.vpc_id
}

// TODO this is for exercise purposes only
//resource "aws_security_group_rule" "ecs_to_rds" {
//  type                     = "ingress"
//  from_port                = 5432
//  to_port                  = 5432
//  protocol                 = "TCP"
//  source_security_group_id = var.ecs_security_group_id
//  security_group_id        = aws_security_group.rds.id
//}

// TODO this is for exercise purposes only
resource "aws_security_group_rule" "rds_to_world" {
  type                     = "ingress"
  from_port                = 5432
  to_port                  = 5432
  protocol                 = "TCP"
  cidr_blocks              = ["0.0.0.0/0"]
  security_group_id        = aws_security_group.rds.id
}

// TODO this is for exercise purposes only
resource "aws_security_group_rule" "rds_to_world_egress" {
  type              = "egress"
  from_port         = 0
  to_port           = 0
  protocol          = "-1"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.rds.id
}
