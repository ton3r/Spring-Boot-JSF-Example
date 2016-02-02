package application.service;

import java.util.List;

import application.entity.SystemUser;
import application.repository.SystemUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class SystemUserDetailsService implements UserDetailsService{

    @Autowired
    SystemUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser usuario = repository.findByUser(username);

        //Si el usuario no existe lanzamos un error
        if(usuario == null){
            throw new UsernameNotFoundException(String.format("El usuario %s no existe", username));
        }
        //Obtenemos el rol
        List<GrantedAuthority> roles = AuthorityUtils.createAuthorityList("ROLE_"+usuario.getRole());

        UserDetails userDetails = new User(usuario.getUser(), usuario.getPassword(), roles);

        return userDetails;
    }


}