package com.tim.scientific.portal.back.db.models.security;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "created_screapts")
@Getter
@Setter
public class CreatedScript {

    @Id
    @GeneratedValue()
    private UUID createdScriptId;

    /**Переменная содержашая команду
     * для выполнения обработчиком команд
     * создания новых сущностей в базе данных
     *
     * Пример команды:
     * [{
     *  pageType:"тип сраницы",
     *  name:"имя",
     *  byPageData:"{json}",
     *      previewObject:{ - обьект предпоказа
     *      (нужен если есть модули с переходами на полноценную страницу)
     *      moduleId:"идентификатор модуля с превью объектами"
     *      objectType:"тип объекта"
     *      objectRank:"ранг объекта"
     *      pageLink:"ссылка на страницу - при пустом значении будет вставлена ссылка на создаваемую
     *      страницу"
     *      name:"имя объекта для админитсративного контура"
     *         content:[
     *      key:"ключ для выборки данных из запроса"
     *      name:"имя для административного контура"
     *      contentType:"тип контента"
     *      value:"сохраняемое значение"
     *       ]
     *  }
     *      modules:[ - модули
     *  {
     *  rank:"ранг"
     *  moduleType:"тип модуля"
     *  name:"имя"
     *      modulesObjects:[ - объекты модуля
     *  objectType:"тип объекта"
     *  objectRank:"ранг объекта"
     *  pageLink:"ссылка объекта"
     *  name:"название объекта"
     *      content:[ - контент
     *  key:"ключ контента - по нему из запроса вставляется значение и считается "
     *  name:"имя"
     *  contentType:"тип"
     *  value:"знчение"
     *  ]
     *  ]
     *  }
     *  ]
     *  }
     * ]*/
    @Type(type = "jsonb-node")
    @Column(columnDefinition = "jsonb")
    private JsonNode command;
}