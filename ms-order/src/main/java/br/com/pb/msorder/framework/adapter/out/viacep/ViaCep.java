package br.com.pb.msorder.framework.adapter.out.viacep;

import br.com.pb.msorder.domain.model.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(url = "https://viacep.com.br/ws/", name = "viacep")
public interface ViaCep {
    @GetMapping("{cep}/json")
    Address findAddressByCep(@PathVariable("cep") String cep);
}
