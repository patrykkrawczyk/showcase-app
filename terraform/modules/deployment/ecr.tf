resource "aws_ecr_repository" "service_docker_repository" {
  name = "${var.service_name}-${var.environment}"
}

resource "aws_ecr_lifecycle_policy" "service-docker-repository-policy" {
  repository = aws_ecr_repository.service_docker_repository.name

  policy = <<EOF
{
    "rules": [
        {
            "rulePriority": 1,
            "description": "Expire images older than 5 latest builds",
            "selection": {
                "tagStatus": "any",
                "countType": "imageCountMoreThan",
                "countNumber": 5
            },
            "action": {
                "type": "expire"
            }
        }
    ]
}
EOF
}
