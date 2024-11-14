package project.movie.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import project.movie.store.domain.item.*;

import java.io.File;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ItemDataInitializer implements ApplicationRunner {
    private final ItemService itemService;


    @Override
    public void run(ApplicationArguments args){
        File popcornFile = new File("src/main/resources/static/images/popcorn_640.png");
        File ticketFile = new File("src/main/resources/static/images/ticket_640.png");
        File comboFile = new File("src/main/resources/static/images/popcorn-ticket_640.png");

        List<Item> existingItems = itemService.itemFindAll();


        //itemCode값은 정적인 이미지 1개당 적용 (기본키이므로, 중복안되게 설정)
        if (existingItems.isEmpty()){
            Item popcornItem = new Item(
                    1, ItemType.FOOD,
                    "popcorn","popcorn 1개",
                    ItemPlaceType.NATIONWIDE, ExpConfig.dateSet(120),
                    8000,1000,popcornFile.getPath(),
                    ItemStatus.PROGRESS);

            Item ticketItem = new Item(
                    2, ItemType.TICKET,
                    "ticket","movie ticket 1장",
                    ItemPlaceType.NATIONWIDE, ExpConfig.dateSet(120),
                    15000,1000,ticketFile.getPath(),
                    ItemStatus.PROGRESS);

            Item comboItem = new Item(
                    3, ItemType.COMBO,
                    "combo","movie ticket 2장 + popcorn 1개 콤포구성",
                    ItemPlaceType.NATIONWIDE, ExpConfig.dateSet(120),
                    20000,1000,comboFile.getPath(),
                    ItemStatus.PROGRESS);


            itemService.itemSave(popcornItem);
            itemService.itemSave(ticketItem);
            itemService.itemSave(comboItem);
        }
    }
}
