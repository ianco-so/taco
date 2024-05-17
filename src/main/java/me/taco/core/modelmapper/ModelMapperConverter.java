package me.taco.core.modelmapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperConverter {

    @Autowired
    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return this.modelMapper;
    }

    public <S,T> List<T> mapToList(List<S> source, Class<T> targetClass){
        return source
            .stream()
            .map(element -> modelMapper.map(element, targetClass))
            .collect(Collectors.toList());

    }

    public <T, S> T mapToObject(S source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

}
