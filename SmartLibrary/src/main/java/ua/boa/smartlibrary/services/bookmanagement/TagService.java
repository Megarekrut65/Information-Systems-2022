package ua.boa.smartlibrary.services.bookmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.PageGetter;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Genre;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Tag;
import ua.boa.smartlibrary.db.repositories.bookmanagement.TagRepository;
import ua.boa.smartlibrary.exceptions.bookmanagement.TagNotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TagService extends PageGetter<Tag> {
    @Autowired
    private TagRepository repository;

    public Tag create(String name) {
        return repository.save(new Tag(name));
    }

    public List<Tag> getAll() {
        return repository.findAll();
    }

    public Tag update(Integer id, String name) {
        Tag tag = get(id);
        tag.setName(name);
        return repository.save(tag);
    }

    public void remove(Integer id) {
        Tag tag = get(id);
        repository.delete(tag);
    }

    public Tag get(Integer id) {
        Optional<Tag> optional = repository.findById(id);
        if (optional.isEmpty()) throw new TagNotFoundException(id);
        return optional.get();
    }

    public List<Tag> findByName(String name) {
        return repository.findTagsByNameAdvanced(name);
    }
    public List<Tag> getPage(int page, int perPage, List<Tag> tags) {
        return getPart(tags, page, perPage, Comparator.comparing(Tag::getName));
    }
}

