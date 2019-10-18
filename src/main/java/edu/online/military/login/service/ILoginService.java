package edu.online.military.login.service;

import com.jfinal.plugin.activerecord.Record;

public interface ILoginService {
    Record getUserByCardId(String cardId);
}
