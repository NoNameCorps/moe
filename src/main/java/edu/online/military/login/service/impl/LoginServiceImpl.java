package edu.online.military.login.service.impl;

import com.jfinal.plugin.activerecord.Record;
import edu.online.military.login.dao.LoginDao;
import edu.online.military.login.service.ILoginService;

public class LoginServiceImpl implements ILoginService {
    private LoginDao loginDao = new LoginDao();
    @Override
    public Record getUserByCardId(String cardId) {
        return loginDao.getUserByCardId(cardId);
    }
}
