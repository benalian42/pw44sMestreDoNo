package br.edu.utfpr.pb.pw44s.server.service.impl;

import br.edu.utfpr.pb.pw44s.server.model.Address;
import br.edu.utfpr.pb.pw44s.server.model.User;
import br.edu.utfpr.pb.pw44s.server.repository.AddressRepository;
import br.edu.utfpr.pb.pw44s.server.repository.UserRepository;
import br.edu.utfpr.pb.pw44s.server.service.IAddressService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Address save(Address address, String username) {
        User user = userRepository.findUserByUsername(username);
        if (user != null) {
            address.setUserId(user.getId());
        }
        return addressRepository.save(address);
    }

    @Override
    public List<Address> findByUser(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user != null) {
            return addressRepository.findByUserId(user.getId());
        }
        return Collections.emptyList();
    }
}
