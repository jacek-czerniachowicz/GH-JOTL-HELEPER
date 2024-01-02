package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.entities.InviteCodeEntity;
import com.gloomhaven.helper.repository.InviteCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InviteCodeService {
    private final InviteCodeRepository repository;

    public Long findRoomId(String code){
        Optional<InviteCodeEntity> invCode = repository.findByCode(code);
        if (invCode.isPresent()){
            return invCode.get().getRoomId();
        }
        return -1L;
    }
    public String findCode(Long id){
        Optional<InviteCodeEntity> invCode = repository.findByRoomId(id);
        if (invCode.isPresent()){
            return invCode.get().getCode();
        }
        return "";
    }
    public InviteCodeEntity addInviteCode(Long id, String code){
        InviteCodeEntity inviteCode = new InviteCodeEntity();
        inviteCode.setRoomId(id);
        inviteCode.setCode(code);

        System.out.println(inviteCode);
        return repository.save(inviteCode);
    }

    public List<InviteCodeEntity> findAll(){
        return repository.findAll();
    }
}
