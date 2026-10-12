package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.dto.AddressDTO;
import br.edu.utfpr.pb.pw44s.server.mapper.AddressMapper;
import br.edu.utfpr.pb.pw44s.server.model.Address;
import br.edu.utfpr.pb.pw44s.server.service.IAddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("addresses")
public class AddressController {

    private final IAddressService addressService;
    private final AddressMapper addressMapper;

    public AddressController(IAddressService addressService, AddressMapper addressMapper) {
        this.addressService = addressService;
        this.addressMapper = addressMapper;
    }

    @PostMapping
    public ResponseEntity<AddressDTO> save(@RequestBody @Valid AddressDTO addressDTO, Principal principal) {
        Address address = addressMapper.toEntity(addressDTO);
        Address saved = addressService.save(address, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(addressMapper.toDto(saved));
    }

    @GetMapping
    public ResponseEntity<List<AddressDTO>> findByUser(Principal principal) {
        List<Address> addresses = addressService.findByUser(principal.getName());
        return ResponseEntity.ok(addresses.stream().map(addressMapper::toDto).collect(Collectors.toList()));
    }
}
