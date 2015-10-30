var RulesBox = React.createClass({
  render: function() {
    return (
      <div className="mainBox">        
        <b>Кланова війна це найголовніша діяльність клану. </b>

<b>Мета кланової війни</b>:

Забезпечити перемогу у клановій війні.

Набрати максимальну к-сть очків досвіду клану. Очки досвіду клану підсилюють увесь клан 

шляхом досягнення вищого рівня клану.

Навчання учасників клану грі.

НЕ є метою кланової війни:

Збір ресурсів

Задоволення особистих амбіцій.

КВ поділяються на адміністровані та неадміністорвані

Адміністрована кв проводиться лідером, колідером або тимчасовим колідером, що згоден її 

адмініструвати. Включає в себе облік: к-сть атака, к-сть зірок, та порушення по кожному учаснику 

кв.

Неадміністорвана кв ніяким чином не контролюється і  її результати не мають ніяких 

адміністративних наслідків. Проводиться тимчасовими колідерами які не згодні адмініструвати кВ.

Керує клановою війною керівник кланової війни:

у планових війна – колідер або старшина, якому це делегував колір ер

у непланових війнах – колідер або старшина, що зголосився її проводити за згоди керівництва 

клану. Для цього достатньо згоди одного колідера за відсутності заперечень інших колідерів.

<b>Функція керівника клановою війною:</b>

Забезпечення досягнення мети кланової 

Розподіл мішеней для атаки, їх облік та інформування про них

Організація надання підмоги в захист та атаку

Вирішення поточних питань кланової війни та конфліктних ситуацій

Організація учасників клану

Облік досягнень та порушень гравців

Надання порад гравцям

Функція помічника у клановій війні допомагати керівнику кланової війни за його прямими 

вказівками

Графік проведення кланових воєн:

Планова – четвер близько 20.00

Непланові  - за бажання гравців, але так, щоб не суперечити плановій війні.

Бажана к-сть кланових воєн на тиждень  - 3.

Участь у плановій клановій війні обов’язкова. 

Гравець, що не може брати участь у клановій війні повинен позначити себе як неактивного 

(червоним). Тривалий чи безпідставний пропуск планової кланової війни є підставою до 

застосування санкцій до гравця, включаючи видалення з клану.

Участь у неплановій війні є добровільною та здійснюється за записом. 

Запис на непланову війну проводиться у чаті та на форумі. 

Інформування про кланову війну здійснюється клановою поштою та на форумі клану.

Якщо кількість учасників кланової війни є некратною 5, то вона доводиться до такої керівником 

кланової війни шляхом включення «червоних» гравців з найвищим рівнем особистого досвіду.

Якщо неможливо довести к-сть учасників кланової війни до кратної 5 за рахунок «червоних» 

гравців, це досягається шляхом не включення гравців з найменшим особистим досвідом.

При цьому «червоні» гравці не зобов’язані здійснювати атаки і не несуть за це відповідальності. 

Підмога на кланову війну є обов’язком усіх учасників кланової війни. Розподіл (вирішення хто 

кому що має дати) визначається керівником кланової війни. При цьому керівник кланової війни не 

може призначати іншим учасникам кв роздати більше підмоги ніж він сам. Гравець за згоди 

керівника кВ може добровільно роздати підмогу за інших.

<b>Дисципліна кланової війни вимагає безумовного виконання директив керівника кланової війни.</b>

Гравці зобов’язуються виконати 2 атаки.

Гравці зобов’язуються виконати атаки на мішені узгоджені з керівником чи помічниками керівника 

кланової війни.

Гравці зобов’язуються здійснювати атаки незалежно до особистої ресурсної вигоди.

За узгодженням з керівником кланової війни чи його помічниками можливий самовідвід від 

складних атак у зв’язку із ресурсними обмеженнями гравця:

Якщо немає ресурсів

Якщо йде збір ресурсів на будівництво важливої споруди

Проте такі повторювані дії можуть стати підставою для дисциплінарних висновків з боку 

керівництва клану.

Гравці зобов’язуються докладати усіх зусиль для отримання максимального результату у клановій 

війні та окремій атаці.

Гравці зобов’язуються атакувати виключно за умов отримання повного підкріплення, окрім 

випадків, коли результат досягається і без нього.

<b>Організація кланової війни</b>

До початку кланової війни керівник кланової війни повинен через чат, кланову розсилку та форум 

клану надати інформацію щодо правил проведення конкретної війни:

Час початку

Керівники та помічники

Правила атаки

Час початку т проміжки запису на атаку

На виконання першої атаки надається 12 годин від початку стадії війни. Протягом цього часу за 

гравцем зберігається виключне право на атаку вказаної мішені.

Друга атака може бути погоджена керівником кланової війни після проведення першої атаки за 

рішенням керівника або одразу або після перших 12 годин стадії війни.

На виконання другої атаки надається 10 годин (бронь знімається за 2 години до завершення 

кланової війни).

В останні 2 годин здійснюються резервні атаки.   
      </div>
    );
  }
});


React.render(<RulesBox />, document.getElementById('rulesNode'));