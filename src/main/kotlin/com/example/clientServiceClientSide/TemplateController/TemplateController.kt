package com.example.clientServiceClientSide.TemplateController

import com.example.clientServiceClientSide.ClientModel.Client
import mu.KotlinLogging
import org.springframework.http.HttpEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.client.RestTemplate
import java.text.SimpleDateFormat

//A service class to implement remote deletion
class Id_Getter(var id: String = "") {
}

const val URL_SERVICE = "http://localhost:8080/clients/"
private val logger = KotlinLogging.logger {}
@Controller
class ClientController {

    //Simple redirector to start page
    @GetMapping("/")
    fun redirect(): String {
        return "start"
    }

    @GetMapping("/start")
    fun getStartPage() ="start"

    @GetMapping("/getallclients")
    fun getAllClients(model: Model, id_gr: Id_Getter): String
    {
        logger.info("Attempted to list all clients")
        val restTemplate = RestTemplate()
        val clientResourceUrl = URL_SERVICE + "listall"
        val clients_list = restTemplate.getForObject("$clientResourceUrl", Array<Client>::class.java)
        model.addAttribute("tmp", clients_list)
        return "getAllClients"
    }

    @GetMapping("/addclient")
    fun showClientCreationForm(model: Model): String {
        logger.info("Called a form to load a new client")
        model.addAttribute("tmp", Client())
        return "clientAdditionForm"
    }

    @PostMapping("/addclient")
    fun clientCreate(@ModelAttribute client: Client, model: Model): String {
        logger.info("Attempt to create a new client")
        client.dr = SimpleDateFormat("yyyy-MM-dd").parse(client.bd)
        model.addAttribute("tmp", client)
        val restTemplate = RestTemplate()
        val request = HttpEntity(client)
        restTemplate.postForObject(URL_SERVICE + "add",
                request, Client::class.java)
        logger.info("Created new client")
        return "start"
    }

    @GetMapping("/getclient")
    fun showClientSearchByIdForm(model: Model): String
    {
        logger.info("Called a form to search client by id")
        model.addAttribute("id", Id_Getter())
        return "clientGetForm"
    }

    @PostMapping("/getclient")
    fun clientSearchById(model: Model, id_gr: Id_Getter): String
    {
              try {
                  val restTemplate = RestTemplate()
                  val clientResourceUrl = URL_SERVICE + id_gr.id
                  val client = restTemplate.getForObject("$clientResourceUrl", Client::class.java)
                  model.addAttribute("tmp", client)
                  logger.info("Client found by id "+client?.name)
                  return "clientGetResult"
              }
        catch (e:org.springframework.web.client.HttpClientErrorException)
        {
            logger.error("Client not found by id")
            return "errorClientNotFound"
        }
    }

    @GetMapping("/deleteclient")
    fun showClientDeleteByIdForm(model: Model): String
    {
        logger.info("Called a form to delete a client")
        model.addAttribute("id", Id_Getter())
        return "clientDelete"
    }

    @PostMapping("/deleteclient")
    fun clientDeleteById(model: Model, id_gr: Id_Getter): String
    {
        logger.info("Deleting client by id "+id_gr.id)
        val restTemplate = RestTemplate()
        val clientResourceUrl = URL_SERVICE + "del"
        val request = HttpEntity(id_gr)
        val client = restTemplate.postForObject("$clientResourceUrl", request, Id_Getter::class.java)
        model.addAttribute("tmp", client)
        return "start"
    }
}