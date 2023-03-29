package charel.samuel.kotlin.playground

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}

@RestController
class MessageResource(val messageService: MessageService) {
	@GetMapping("/messages")
	fun index(): List<Message> {
		return messageService.findMessages()
	}

	@PostMapping("/messages")
	fun post(@RequestBody message: Message) {
		return messageService.post(message)
	}
}

@Service
class MessageService(val db: MessageRepository) {
	fun findMessages(): List<Message> {
		return db.findMessages()
	}

	fun post(message: Message) {
		db.save(message)
	}
}

interface MessageRepository: CrudRepository<Message, String> {
	@Query("select * from messages")
	fun findMessages(): List<Message>
}

@Table("MESSAGES")
data class Message(@Id val id: String?, val text: String)
