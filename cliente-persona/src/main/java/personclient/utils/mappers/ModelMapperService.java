package personclient.utils.mappers;

import org.modelmapper.ModelMapper;
public interface ModelMapperService {
    ModelMapper forResponse();
    ModelMapper forRequest();
}
