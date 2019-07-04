package com.wj.core.service.card;

import com.google.common.collect.Lists;
import com.wj.core.entity.card.OpCard;
import com.wj.core.entity.card.PadModule;
import com.wj.core.entity.card.dto.CardDTO;
import com.wj.core.entity.card.dto.CardDetailDTO;
import com.wj.core.entity.card.dto.CardServicesDTO;
import com.wj.core.entity.card.dto.CreateCardDTO;
import com.wj.core.entity.card.enums.CardStatus;
import com.wj.core.entity.card.enums.CardType;
import com.wj.core.entity.op.OpService;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.repository.card.CardRepository;
import com.wj.core.repository.card.PadModuleRepository;
import com.wj.core.repository.op.ServeRepository;
import com.wj.core.service.user.UserFamilyService;
import com.wj.core.util.jiguang.JPush;
import com.wj.core.util.mapper.BeanMapper;
import com.wj.core.util.time.ClockUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class CardService {

    @Autowired
    private UserFamilyService userFamilyService;
    @Autowired
    private PadModuleRepository padModuleRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ServeRepository serveRepository;
    @Value("${wj.oss.access}")
    private String url;


    // 获取用户卡片
    public List<CardDTO> getUserCard(Integer userId) {
        List<OpCard> list = cardRepository.findByUserCards_UserInfo_IdAndUserCards_IsShowAndStatusOrderByLocationAsc(userId, CardStatus.YES, CardStatus.YES);
        List<CardDTO> cardDTOS = Lists.newArrayList();
        for (OpCard op : list) {
            CardDTO cd = BeanMapper.map(op, CardDTO.class);
            cd.setIsShow(op.getUserCards().get(0).getIsShow());
            cardDTOS.add(cd);
        }
        return cardDTOS;
    }

    // 获取所有卡片
    public List<OpCard> getCardList(Integer userId) {
        return cardRepository.findByUserCards_UserInfo_IdAndStatusOrderByLocationAsc(userId, CardStatus.YES);
    }

    // 保存用户卡片
    @Transactional
    public void saveUserCard(Integer userId, Integer id) {
        cardRepository.modityCardStatus(CardStatus.YES.ordinal(), userId, id);
    }

    // 删除用户卡片
    @Transactional
    public void removeUserCard(Integer userId, Integer cardId) {
        cardRepository.modityCardStatus(CardStatus.NO.ordinal(), userId, cardId);
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

    @Transactional
    public void saveCard(CreateCardDTO cardDTO) {
        OpCard card = BeanMapper.map(cardDTO, OpCard.class);
        card.setCreateDate(ClockUtil.currentDate());
        card.setStatus(CardStatus.YES);
        if (cardDTO.getCardType().equals("0")) {
            card.setType(CardType.OP);
        }
        if (cardDTO.getCardType().equals("1")) {
            card.setType(CardType.WU);
        }
        if (cardDTO.getCardType().equals("2")) {
            card.setType(CardType.IU);
        }
        if (cardDTO.getCardType().equals("3")) {
            card.setType(CardType.IMG);
        }
        if (StringUtils.isNotBlank(cardDTO.getPath())) {
            card.setIcon(url + cardDTO.getPath());
        }
        // location 顺序
        if (card.getLocation() == 0) {
            OpCard lastOpCard = cardRepository.findFirstByOrderByLocationDesc();
            if (lastOpCard == null) {
                card.setLocation(1);
            } else {
                card.setLocation(lastOpCard.getLocation() + 1);
            }

        } else {
            List<OpCard> opCardList = cardRepository.findByLocationIsGreaterThanEqual(card.getLocation());
            opCardList.forEach(opCard -> {
                cardRepository.modityLocation(opCard.getLocation() + 1, opCard.getId());
            });
        }
        OpCard opCard = cardRepository.save(card);
        List<SysUserFamily> userFamilyList = userFamilyService.findByIdentity(1);
        userFamilyList.forEach(userFamily -> {
            Integer userId = userFamily.getUserFamily().getUserId();
            cardRepository.insertUserCard(userId, opCard.getId(), CardStatus.YES.ordinal());
        });
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JPush.sendCardSchedulePush("card_schedule_push_" + opCard.getId(), formatter.format(opCard.getPushDate()), "add card message");
    }

    @Transactional
    public void removeCard(Integer id) {
        cardRepository.removeUserCard(id);
        cardRepository.modityCardStatus(CardStatus.NO.ordinal(), id);
        JPush.sendPushAll("CARD", "remove card message");
    }


    public Page<OpCard> getList(Integer pageNum, Integer pageSize, Integer type, Integer status) {
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();
            if (type != null) {
                if (type == 0) {
                    predicates.add(criteriaBuilder.equal(root.get("type"), CardType.OP));
                }
                if (type == 1) {
                    predicates.add(criteriaBuilder.equal(root.get("type"), CardType.WU));
                }
                if (type == 2) {
                    predicates.add(criteriaBuilder.equal(root.get("type"), CardType.IU));
                }
                if (type == 3) {
                    predicates.add(criteriaBuilder.equal(root.get("type"), CardType.IMG));
                }
            }
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pageable page = PageRequest.of(pageNum - 1, pageSize, Sort.Direction.DESC, "createDate");
        Page<OpCard> pageCard = cardRepository.findAll(specification, page);
        return pageCard;
    }

    public List<PadModule> getPadModuleList() {
        return padModuleRepository.findAll();
    }

}
