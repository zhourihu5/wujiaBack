package com.wj.core.service.card;

import com.google.common.collect.Lists;
import com.wj.core.entity.card.OpCard;
import com.wj.core.entity.card.UserCard;
import com.wj.core.entity.card.dto.CardDTO;
import com.wj.core.entity.card.dto.CardDetailDTO;
import com.wj.core.entity.card.dto.CardServicesDTO;
import com.wj.core.entity.card.enums.CardStatus;
import com.wj.core.entity.op.OpService;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.repository.card.CardRepository;
import com.wj.core.repository.op.ServeRepository;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.util.mapper.BeanMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ServeRepository serveRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Value("${wj.image.url}")
    private String imgUrl;


    // 获取用户卡片
    public List<CardDTO> getUserCard(String token) {
        List<OpCard> list = cardRepository.findByUserCards_UserInfo_IdAndUserCards_IsShow(1, CardStatus.YES);
        List<CardDTO> cardDTOS = Lists.newArrayList();
        for (OpCard op : list) {
            CardDTO cd = BeanMapper.map(op, CardDTO.class);
            cd.setIsShow(op.getUserCards().get(0).getIsShow());
            cardDTOS.add(cd);
        }
        return cardDTOS;
    }

    // 获取所有卡片
    public List<OpCard> getCardList(String token) {
        return cardRepository.findByUserCards_UserInfo_Id(1);
    }

    // 保存用户卡片
    @Transactional
    public void saveUserCard(String token, Integer id) {
        cardRepository.modityCardStatus(CardStatus.YES.ordinal(), 1, id);
    }

    // 删除用户卡片
    @Transactional
    public void removeUserCard(String token, Integer cardId) {
        cardRepository.modityCardStatus(CardStatus.NO.ordinal(), 1, cardId);
    }

    public CardDetailDTO getCardDetail(Integer id) {
        OpCard card = cardRepository.getOne(id);
        CardDetailDTO cardDetailDTO = new CardDetailDTO();
        cardDetailDTO.setId(card.getId());
        cardDetailDTO.setContent(card.getContent());
        String services = card.getServices();
        if (StringUtils.isNotBlank(services)) {
            String[] sidAry = services.split(",");
            List<CardServicesDTO> servicesDTOList = Lists.newArrayList();
            for (String sid : sidAry) {
                OpService service = serveRepository.getOne(Integer.valueOf(sid));
                CardServicesDTO csDTO = BeanMapper.map(service, CardServicesDTO.class);
                servicesDTOList.add(csDTO);
            }
            cardDetailDTO.setServices(servicesDTOList);
        }
        return cardDetailDTO;
    }

}
