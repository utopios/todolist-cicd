package com.example.correctiontodolist.service;

import com.example.correctiontodolist.entity.Image;
import com.example.correctiontodolist.entity.Todo;
import com.example.correctiontodolist.exception.EmptyFieldsException;
import com.example.correctiontodolist.exception.TodoNotFoundException;
import com.example.correctiontodolist.repository.ImageRepository;
import com.example.correctiontodolist.repository.TodoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    @Autowired
    private TodoRespository _todoRespository;

    @Autowired
    private ImageRepository _imageRepository;

    @Autowired
    private UploadService uploadService;

    public Todo createTodo(String title, String description) throws Exception {
        if(title == null || description == null) {
            throw EmptyFieldsException.withFields("title");
        }
        Todo todo = new Todo(title, description);
        return _todoRespository.save(todo);
    }

    public Todo createTodo(String title, String description, List<MultipartFile> images) throws EmptyFieldsException, IOException {
        if(title == null || description == null) {
            throw EmptyFieldsException.withFields("title");
        }
        Todo todo = new Todo(title, description);

        if(_todoRespository.save(todo) != null) {
            for(MultipartFile img : images) {
                Image image =new Image();
                image.setUrl(uploadService.store(img));
                image.setTodo(todo);
                _imageRepository.save(image);
                //todo.getImages().add(image);
            }
            return todo;
        }
        return null;
    }

    public Todo updateTodo(int id, String title, String description) throws Exception {
        Optional<Todo> optionalTodo = _todoRespository.findById(id);
        if(optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();
            todo.setTitle(title);
            todo.setDescription(description);
            return _todoRespository.save(todo);
        }

        throw new TodoNotFoundException();
    }

    public boolean deleteTodo(int id) throws TodoNotFoundException {
        Optional<Todo> optionalTodo = _todoRespository.findById(id);
        if(optionalTodo.isPresent()) {
            _todoRespository.delete(optionalTodo.get());
            return true;
        }

        throw new TodoNotFoundException();
    }

    public boolean updateStatus(int id) throws Exception {
        Todo todo = _todoRespository.findById(id).get();
        if(todo != null) {
            todo.setStatus(!todo.isStatus());
            _todoRespository.save(todo);
            return true;
        }
        throw new TodoNotFoundException();
    }

    public List<Todo> getByStatus(boolean status) {
        return _todoRespository.findByStatus(status);
    }

    public Todo getTodoById(int id) {
        return _todoRespository.findById(id).get();
    }
}
