package models

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import play.api.Play.current

object S3 {

    val accessKeyId = Play.configuration.getString("treasurer.aws.access_key_id")
    val secretAccessKey = Play.configuration.getString("treasurer.aws.secret_access_key")
    val awsCreds = new BasicAWSCredentials(accessKeyId, secretAccessKey)
    lazy val s3Client = new AmazonS3Client(awsCreds);
}