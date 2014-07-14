package models

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import play.api.Play
import play.api.Play.current

object S3 {

    val accessKeyId = Play.configuration.getString("treasurer.aws.access_key_id")
    val secretAccessKey = Play.configuration.getString("treasurer.aws.secret_access_key")
    // XXX Do something if missing!
    val awsCreds = new BasicAWSCredentials(accessKeyId.get, secretAccessKey.get)
    lazy val s3Client = new AmazonS3Client(awsCreds);
}