var RulesBox = React.createClass({
  render: function() {
    return (
      <div className="mainBox">   
      <h3>Кланова війна (КВ) це найголовніша діяльність клану.</h3>
<b>КВ проводяться</b>:
Четвер 8 вечора - всі зелені (статус в профілі "я в КВ") + добирається до круглого числа червоні з найнищим рівнем.
<br/>
Вівторок 8 вечора - по запису на сайті для всіх бажаючих при умові що є бажаючі проводити. При нерівній кількості беруть перших хто записався.
<br/>
Четвер 8 вечора - по запису на сайті для всіх бажаючих при умові що є бажаючі проводити.При нерівній кількості беруть перших хто записався.
<br/>
Досвідчені ведучі можуть мінімально відхилятись від цих правил, наприклад трохи відкласти початок кв щоб добрати людей до круглого числа чи не 
взяти зеленого гравця рашера з сильним дисбалансом бази, тощо)
<br/>
<br/>

<b>Атака в КВ виключно з дозволу ведучого</b>.
<br/>
Стандартно заявки на атаку подаються на сайті ("Побажання").
<br/>
Заявка вважається підтвердженою коли вона зявилась в категорії ("Підтверджені бронювання").
<br/>
Ведучий на свій розсуд може приймати і підтверджувати заявки в чаті клану.
<br/>
Метою атаки є 3 зірки.
<br/>
В атаку обов'язково брати підмогу.
<br/>
Просячи підмогу переконайтесь що ви її не втратите на посторонні цілі (у вас має бути щит або ви плануєте атакувати відразу після отримання підмоги).
<br/>
Просіть конкретно - наприклад кулі максимального рівня, а не щось сильне на кв.
<br/>
Атакувати максимальною, сильною армією (з підмогою).
<br/>
Гравець має здійснити 2 атаки якщо інше не дозволено ведучим кв.
<br/>
Якщо підмогу не дають відразу - почекайти - спонсори живі люди які не можуть неперервно знаходитись в грі.
<br/>
Досвідчений гравець може в атаці відступати від правил якщо здобуде 3 зірки всеодно (іноді стратегія КВ вимагає атакувати заслабі бази щоб отримати 
кращий результат).
        <br/>
Важливим елементом атаки в кв і ознакою мінімальної кваліфікації є виманювання і знищення гарнізону з замку противника в якості першої фази атаки.
<br/>
До наступної фази гравець не переходить доки не ворожу підмогу не знищено повністю.
<br/>
Лише окремі види атак, як наприклад атака драконами виманювання не вимагає.
<br/>

Заборонено давати донат в КВ в захист без узгодження з ведучим. У нас вироблена схема згідно якої в КВ дається максимально сильна підмога.
<br/>
Не в КВ заборонено давати донат який не просять. На приклад дати варварів коли просять лучників.
<br/>
Не на КВ не забороронено просити щось інше але рекомендується просити луків, варварів, гоблінів.
<br/>
Якщо просите щось дорожче старайтесь віддячувати відповідно і назагал система донату це взяємодопомога а не фінансування одних гравців іншими.
<br/>
Просто кажучи - хочете щоб вам давали підмогу - давайте самі іншим.
<br/>
Заборонений спам і мат та будь яка інша форма агресії чи неповаги до інших гравців.
<br/>
Не дозволена зміна звань (як повишення так пониження). Будьяка зміна звань здійснюється виключно гравцями Highlander i Jareky 
відповідно до детально розроблених правил.
        <br/>
Не дозволено видалення гравців з клану (здійснюється виключно Highlander i Jareky).
<br/>
Дозволено прийом гравців (підтвеодження заявок) якщо в клані менше 45 гравців.
<br/>
При кількості гравців 45 і більше прийом здійснюють Highlander i Jareky.
<br/>
Не запрошуйте незнайомих гравців з глобального чату - перевірено що це приносить багато більше шкоди і затрат часу ніж користі.
<br/>
Не приймайте гравців де відверто видно що вони іноземці (арабська мова наприклад) - ви не зможете з ними порозумітись.
<br/>
Звання старшини дається за 10 здобутих зірок за клан. Зараз розглядається варіант поміняти на 5 тризіркових атак.
<br/>
Звання сорука/колідера не просити. Надається людям які фактично виконують роботу сорука/колідера на розсуд Highlander i Jareky.
<br/>
За порушення правил (в першу чергу участі в КВ) існує система штрафів - попередження, пониження (якщо є куди, інакше цей крок опускається), 
видалення на 1 тиждень (кожне наступне видалення на 1 тиждень довше).
<br/>
   
      </div>
    );
  }
});


React.render(<RulesBox />, document.getElementById('rulesNode'));