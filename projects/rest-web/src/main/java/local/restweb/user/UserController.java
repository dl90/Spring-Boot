package local.restweb.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @GetMapping()
    public List<User> getAll() {
        return userDAO.findAll();
    }

    @GetMapping("/{id}")
    // EntityModel HATEOAS
    public EntityModel<User> getById(@PathVariable(name = "id") int id) throws UserNotFoundException {
        User user = userDAO.findOne(id);

        EntityModel<User> model = EntityModel.of(user);
        WebMvcLinkBuilder allUsers = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(this.getClass()).getAll());

        model.add(allUsers.withRel("all_users"));

        return model;
    }

    @GetMapping("/{id}/msg1")
    public User getUserMsg1(@PathVariable(name = "id") int id) throws UserNotFoundException {
        User user = userDAO.findOne(id);
        return UserFilter.filter(user, UserFilter.Filters.EXCLUDE_MSG2);
    }

    @GetMapping("/{id}/msg2")
    public User getUserMsg2(@PathVariable(name = "id") int id) throws UserNotFoundException {
        User user = userDAO.findOne(id);
        return UserFilter.filter(user, UserFilter.Filters.EXCLUDE_MSG1);
    }

    @PostMapping()
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        int id = userDAO.add(user);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(id).toUri();

        // return 201 and set location header with resource URL (localhost:*/users/{id})
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable(name = "id") int id) throws UserNotFoundException {
        User user = userDAO.delete(id);
        if (user == null)
            throw new UserNotFoundException("id=%d".formatted(id));
    }

}
