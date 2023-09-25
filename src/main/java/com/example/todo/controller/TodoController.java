package com.example.todo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.todo.dto.ResponseDTO;
import com.example.todo.dto.TodoDTO;
import com.example.todo.model.TodoEntity;
import com.example.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("todo")
public class TodoController {
	@Autowired
	private TodoService service;

	@PostMapping
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
		try {
			/*
			 * POST localhost:8080/todo { "title" : "My first todo", "done" : false }
			 */
			log.info("Log:createTodo entrance");
			// dto �� �̿��� ���̺� �����ϱ� ���� entity�� �����Ѵ�.
			TodoEntity entity = TodoDTO.toEntity(dto);
			log.info("Log:dto => entity ok!");
			// entity userId�� �ӽ÷� �����Ѵ�.
			entity.setUserId("temporary-user");
			// service.create �� ���� repository �� entity�� �����Ѵ�.
			// �̶� �Ѿ��� ���� ���� ���� �����Ƿ� List�� �ƴ� Optional �� �Ѵ�.
			Optional<TodoEntity> entities = service.create(entity);
			log.info("Log:serivce.create ok!");
			// entities �� dtos �� ��Ʈ�� ��ȯ�Ѵ�.
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			log.info("Log:entities => dtos ok!");
			// Response DTO�� �����Ѵ�.
			/*
			 * { "error" : null, "data": [ { "id" : "402809817ed71ddf017ed71dfe720000",
			 * "title" : "My first todo", "done" : false } }
			 */
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			log.info("Log:responsedto ok!");
			// HTTP Status 200 ���·� response �� �����Ѵ�.
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping
	public ResponseEntity<?> retrieveTodoList() {
		String temporaryUserId = "temporary-user";
		List<TodoEntity> entities = service.retrieve(temporaryUserId);
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		// HTTP Status 200 ���·� response �� �����Ѵ�.
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/update")
	public ResponseEntity<?> update(@RequestBody TodoDTO dto) {
		try {
			/*
			 * POST localhost:8080/todo/update { "id" : "????????" "title" :
			 * "Update first todo", "done" : true }
			 */
			// dto �� �̿��� ���̺� �����ϱ� ���� entity�� �����Ѵ�.
			TodoEntity entity = TodoDTO.toEntity(dto);
			// entity userId�� �ӽ÷� �����Ѵ�.
			entity.setUserId("temporary-user");
			// service.create �� ���� repository �� entity�� �����Ѵ�.
			// �̶� �Ѿ��� ���� ���� ���� �����Ƿ� List�� �ƴ� Optional �� �Ѵ�.
			Optional<TodoEntity> entities = service.update(entity);
			// entities �� dtos �� ��Ʈ�� ��ȯ�Ѵ�.
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			// Response DTO�� �����Ѵ�.
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			// HTTP Status 200 ���·� response �� �����Ѵ�.
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}

	@PutMapping
	public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {
		try {
			/*
			 * POST localhost:8080/todo/update { "id" : "????????" "title" :
			 * "Update first todo", "done" : true }
			 */
			// dto �� �̿��� ���̺� �����ϱ� ���� entity�� �����Ѵ�.
			TodoEntity entity = TodoDTO.toEntity(dto);
			// entity userId�� �ӽ÷� �����Ѵ�.
			entity.setUserId("temporary-user");
			// service.create �� ���� repository �� entity�� �����Ѵ�.
			// �̶� �Ѿ��� ���� ���� ���� �����Ƿ� List�� �ƴ� Optional �� �Ѵ�.
			Optional<TodoEntity> entities = service.updateTodo(entity);
			// entities �� dtos �� ��Ʈ�� ��ȯ�Ѵ�.
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			// Response DTO�� �����Ѵ�.
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			// HTTP Status 200 ���·� response �� �����Ѵ�.
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}

	@DeleteMapping
	public ResponseEntity<?> delete(@RequestBody TodoDTO dto){
	try {
	List<String> message =new ArrayList(); 
	String msg = service.delete(dto.getId());
	message.add(msg);
	// Response DTO�� �����Ѵ�.
	ResponseDTO<String> response = 	ResponseDTO.<String>builder().data(message).build();
	return ResponseEntity.ok().body(response);
	}catch (Exception e){
	String error = e.getMessage();
	ResponseDTO<TodoDTO> response = 
	ResponseDTO.<TodoDTO>builder().error(error).build();
	return ResponseEntity.badRequest().body(response);
	}
	}
}
