terraform {
  required_providers {
    aws = {
      source = "hashicorp/aws"
      version = "~> 3.4.0"
    }
    random = {
      source = "hashicorp/random"
      version = "~> 2.3.0"
    }
  }
}

provider "aws" {
  region = "eu-west-1"
}

resource "random_uuid" "randomid" {}

resource "aws_s3_bucket" "terraform_state" {
  bucket = "loki-bay-infrastructure-${random_uuid.randomid.result}"
  
  force_destroy = true

  versioning {
    enabled = true
  }
  
  server_side_encryption_configuration {
    rule {
      apply_server_side_encryption_by_default {
        sse_algorithm = "AES256"
      }
    }
  }
}

output "s3_bucket_name" {
  value = aws_s3_bucket.terraform_state.bucket
}
