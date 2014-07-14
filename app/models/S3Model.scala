package models

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import java.io.File
import java.io.FileInputStream
import play.api.Play
import play.api.Play.current
import scala.collection.JavaConversions._

object S3Model {

    val accessKeyId = Play.configuration.getString("treasurer.aws.access_key_id").get
    val secretAccessKey = Play.configuration.getString("treasurer.aws.secret_access_key").get
    val bucketName = Play.configuration.getString("treasurer.bucket_name").get
    // XXX Do something if missing!
    val awsCreds = new BasicAWSCredentials(accessKeyId, secretAccessKey)
    lazy val client = new AmazonS3Client(awsCreds);

    def createArtifact(project: String, id: String, file: File) = {
        val result = client.putObject(bucketName, project + "/" + id, new FileInputStream(file), new ObjectMetadata())
    }

    def deleteArtifact(project: String, id: String) = {
        val result = client.deleteObject(bucketName, project + "/" + id)
    }

    def getArtifact(project: String, id: String) = {
        val result = client.getObject(bucketName, project + "/" + id)
        Some(Artifact(id = result.getKey, version = "1.2.3", url = "http://www.example.com"))
    }

    def getArtifacts(project: String): Seq[Artifact] = {
        val result = client.listObjects(bucketName, project + "/")
        result.getObjectSummaries.map { summ =>
            Artifact(id = summ.getKey, version = "1.2.3", url = "htp://www.example.com")
        }
    }
}