(function() {
    'use strict';

    angular.module('pages.search')
    .controller('searchPageCtrl', searchPageCtrl);

    /** @ngInject */
    function searchPageCtrl($scope, $log, $state, toastr, $uibModal, baProgressModal, editableOptions, editableThemes, MessageService, MenuParsingService, SessionInfo) {
    	var vm = this;
        vm.disabled = undefined;

        vm.standardItem = {};
        vm.standardSelectItems = [
          {label:'전체', value: 'all'},
          {label:'제목', value: 'title'},
          {label:'ISBN', value: 'isbn'},
          {label:'주제어', value: 'keyword'},
          {label:'목차', value: 'contents'},
          {label:'책소개', value: 'overview'},
          {label:'출판사', value: 'publisher'}
        ];
        
        // vm.withSearchItem = {};
        // vm.selectWithSearchItems = [
        //   {query: 'Hot Dog, Fries and a Soda'},
        //   {query: 'Burger, Shake and a Smile'}
        // ];

        $scope.hasPrev = false;
        $scope.hasNext = false;

        vm.searchQuery = "";


        // TODO
        $scope.bookListTableData = [
    {
      "authors": "버나뎃 로제티 슈스탁",
      "barcode": "KOR9788990794529",
      "category": "유아(0~7세)",
      "contents": "해도 부족한 말, 사랑해!  세상에 오직 하나뿐인 소중하고 사랑스러운 우리 아이를 위한 그림책 『사랑해 사랑해 사랑해』. 아이를 사랑하는 엄마의 마음을 표현한 그림책입니다. 아이의...",
      "datetime": "2013-01-10T00:00:00.000+09:00",
      "ebook_barcode": "",
      "isbn": "8990794528 9788990794529",
      "price": 9500,
      "publisher": "보물창고",
      "sale_price": 8550,
      "sale_yn": "Y",
      "status": "정상판매",
      "thumbnail": "http://t1.daumcdn.net/thumb/R72x100/?fname=http%3A%2F%2Ft1.daumcdn.net%2Fbook%2FKOR9788990794529%3Fmoddttm=20180708072617",
      "title": "사랑해 사랑해 사랑해(아기그림책보물창고1)",
      "translators": "신형건",
      "url": "http://book.daum.net/detail/book.do?bookid=KOR9788990794529"
    },
    {
      "authors": "김난도",
      "barcode": "BOK00011016489IN",
      "category": "시/에세이",
      "contents": "치 앞을 내다볼 수 없는 한국의 청춘들에게 전하는 위로와 격려의 메시지다. 그런 의미에서 이 책은 수많은 청춘의 마음을 울린 김난도 교수의 인생 강의실이라고 할 수 있다.  그는...",
      "datetime": "2010-12-24T00:00:00.000+09:00",
      "ebook_barcode": "",
      "isbn": "8965700035 9788965700036",
      "price": 14000,
      "publisher": "쌤앤파커스",
      "sale_price": 12600,
      "sale_yn": "Y",
      "status": "정상판매",
      "thumbnail": "http://t1.daumcdn.net/thumb/R72x100/?fname=http%3A%2F%2Ft1.daumcdn.net%2Fbook%2FBOK00011016489IN%3Fmoddttm=20180130135031",
      "title": "아프니까 청춘이다",
      "translators": "",
      "url": "http://book.daum.net/detail/book.do?bookid=BOK00011016489IN"
    },
    {
      "authors": 
        "혜민 스님"
      ,
      "barcode": "KOR9788965700609",
      "category": "시/에세이",
      "contents": "진정한 인생의 잠언을 들려주는 혜민 스님의 에세이『멈추면, 비로소 보이는 것들』. 이 책은 관계에 대해, 사랑에 대해, 마음과 인생에 대해 머리로는 알지만 마음으로는 안 되는 것들에...",
      "datetime": "2012-01-27T00:00:00.000+09:00",
      "ebook_barcode": "DGT4808965700609",
      "isbn": "8965700604 9788965700609",
      "price": 14000,
      "publisher": "쌤앤파커스",
      "sale_price": 12600,
      "sale_yn": "Y",
      "status": "정상판매",
      "thumbnail": "http://t1.daumcdn.net/thumb/R72x100/?fname=http%3A%2F%2Ft1.daumcdn.net%2Fbook%2FKOR9788965700609%3Fmoddttm=20171022080619",
      "title": "멈추면 비로소 보이는 것들",
      "translators": "",
      "url": "http://book.daum.net/detail/book.do?bookid=KOR9788965700609"
    },
    {
      "authors": [
        "빅뱅"
      ],
      "barcode": "KOR9788992647601",
      "category": "시/에세이",
      "contents": "세상이 너에게 무언가 허락해주기를 기다리지 말고, 직접 세상을 향해 너를 소리쳐라! 그 무엇도 두려워하지 말고, 당당하게 청춘처럼!  『세상에 너를 소리쳐!』는 아이돌이 아닌, '이 땅의...",
      "datetime": "2009-01-28T00:00:00.000+09:00",
      "ebook_barcode": "",
      "isbn": "8992647603 9788992647601",
      "price": 15000,
      "publisher": "쌤앤파커스",
      "sale_price": 13500,
      "sale_yn": "Y",
      "status": "정상판매",
      "thumbnail": "http://t1.daumcdn.net/thumb/R72x100/?fname=http%3A%2F%2Ft1.daumcdn.net%2Fbook%2FKOR9788992647601%3Fmoddttm=20180708072617",
      "title": "세상에 너를 소리쳐!",
      "translators": [],
      "url": "http://book.daum.net/detail/book.do?bookid=KOR9788992647601"
    },
    {
      "authors": [
        "히가시노 게이고"
      ],
      "barcode": "BOK0001948500811",
      "category": "소설",
      "contents": "것인가 등등 살다보면 한번쯤은 마주하게 되는 어려운 선택의 문제인 것이다. 그런 점에서 이 책이 우리에게 전하는 메시지는 결코 가볍지 않다. 무엇보다 인생의 지도에서 내일에 대한 희망...",
      "datetime": "2012-12-19T00:00:00.000+09:00",
      "ebook_barcode": "DGT4808972756194",
      "isbn": "8972756199 9788972756194",
      "price": 14800,
      "publisher": "현대문학(주)",
      "sale_price": 13320,
      "sale_yn": "Y",
      "status": "정상판매",
      "thumbnail": "http://t1.daumcdn.net/thumb/R72x100/?fname=http%3A%2F%2Ft1.daumcdn.net%2Fbook%2FBOK0001948500811%3Fmoddttm=20180708072617",
      "title": "나미야 잡화점의 기적",
      "translators": [
        "양윤옥"
      ],
      "url": "http://book.daum.net/detail/book.do?bookid=BOK0001948500811"
    },
    {
      "authors": [
        "한상복"
      ],
      "barcode": "KOR9788989313694",
      "category": "자기계발",
      "contents": "문제가 있는 것일까? 11층에서 만난 '인도자'가 그에게 준 카드의 키워드는 무엇일까?  이 책은 너와 내가 경쟁하는 삶이 아니라, 함께 배려하며 사는 삶이야말로 진정한 공존의 길임을 보여...",
      "datetime": "2008-05-01T00:00:00.000+09:00",
      "ebook_barcode": "DGT4808989313694",
      "isbn": "8989313694 9788989313694",
      "price": 10000,
      "publisher": "위즈덤하우스미디어그룹(주)",
      "sale_price": 9000,
      "sale_yn": "Y",
      "status": "정상판매",
      "thumbnail": "http://t1.daumcdn.net/thumb/R72x100/?fname=http%3A%2F%2Ft1.daumcdn.net%2Fbook%2FKOR9788989313694%3Fmoddttm=20180708072617",
      "title": "배려",
      "translators": [],
      "url": "http://book.daum.net/detail/book.do?bookid=KOR9788989313694"
    },
    {
      "authors": [
        "앤서니 브라운"
      ],
      "barcode": "KOR9788901033518",
      "category": "유아(0~7세)",
      "contents": "앤서니 브라운의 명작! 가족의 소중함을 느끼게 해주는 책!  아주 중요한 회사에 다니는 피곳 씨와 아주 중요한 학교에 다니는 두 아들은 집에서는 아무것도 하지 않습니다. 집안일은 모두...",
      "datetime": "2001-10-15T00:00:00.000+09:00",
      "ebook_barcode": "",
      "isbn": "8901033518 9788901033518",
      "price": 9500,
      "publisher": "웅진주니어",
      "sale_price": 8550,
      "sale_yn": "Y",
      "status": "정상판매",
      "thumbnail": "http://t1.daumcdn.net/thumb/R72x100/?fname=http%3A%2F%2Ft1.daumcdn.net%2Fbook%2FKOR9788901033518%3Fmoddttm=20180708072617",
      "title": "돼지책(웅진 세계그림책 1)",
      "translators": [
        "허은미"
      ],
      "url": "http://book.daum.net/detail/book.do?bookid=KOR9788901033518"
    },
    {
      "authors": [
        "박웅현"
      ],
      "barcode": "KOR9788956055466",
      "category": "인문",
      "contents": "책은 무뎌진 우리의 감각을 일깨우는 도끼다!  박웅현 인문학 강독회『책은 도끼다』. 이 책은 ‘책 들여다보기’라는 주제로 이루어진 저자의 강독회를 책으로 정리한 것으로, 인문학으로...",
      "datetime": "2011-10-10T00:00:00.000+09:00",
      "ebook_barcode": "DGT4808956055466",
      "isbn": "8956055467 9788956055466",
      "price": 16000,
      "publisher": "북하우스퍼블리셔스(주)",
      "sale_price": 14400,
      "sale_yn": "Y",
      "status": "정상판매",
      "thumbnail": "http://t1.daumcdn.net/thumb/R72x100/?fname=http%3A%2F%2Ft1.daumcdn.net%2Fbook%2FKOR9788956055466%3Fmoddttm=20180708072617",
      "title": "책은 도끼다",
      "translators": [],
      "url": "http://book.daum.net/detail/book.do?bookid=KOR9788956055466"
    },
    {
      "authors": [
        "주제 사라마구"
      ],
      "barcode": "KOR9788973374939",
      "category": "소설",
      "contents": "주제 사라마구, 인간성의 본질을 묻다!  노벨문학상 수상작가 주제 사라마구의 대표작. 눈먼 자들을 가둔 수용소와 이름없는 도시를 배경으로 인간성의 근원적인 본질에서 가치와 존재, 현대...",
      "datetime": "2015-04-15T00:00:00.000+09:00",
      "ebook_barcode": "DGT4808973374939",
      "isbn": "8973374931 9788973374939",
      "price": 14500,
      "publisher": "해냄출판사",
      "sale_price": 13050,
      "sale_yn": "Y",
      "status": "정상판매",
      "thumbnail": "http://t1.daumcdn.net/thumb/R72x100/?fname=http%3A%2F%2Ft1.daumcdn.net%2Fbook%2FKOR9788973374939%3Fmoddttm=20180707082859",
      "title": "눈먼 자들의 도시",
      "translators": [
        "정영목"
      ],
      "url": "http://book.daum.net/detail/book.do?bookid=KOR9788973374939"
    },
    {
      "authors": [
        "더글라스 케네디"
      ],
      "barcode": "BOK00010503270AL",
      "category": "소설",
      "contents": "달리는 베스트셀러 작가이다. 나고 자란 곳은 미국, 현재 머무르는 곳은 영국의 런던, 그의 책이 가장 잘 팔리는 나라는 프랑스이다. 기이하게도 조국인 미국보다 프랑스를 비롯한 유럽 지역...",
      "datetime": "2017-05-22T00:00:00.000+09:00",
      "ebook_barcode": "DGT4808984371026",
      "isbn": "8984371025 9788984371026",
      "price": 12000,
      "publisher": "밝은세상",
      "sale_price": 10800,
      "sale_yn": "Y",
      "status": "정상판매",
      "thumbnail": "http://t1.daumcdn.net/thumb/R72x100/?fname=http%3A%2F%2Ft1.daumcdn.net%2Fbook%2FBOK00010503270AL%3Fmoddttm=20180708072617",
      "title": "빅 픽처",
      "translators": [
        "조동섭"
      ],
      "url": "http://book.daum.net/detail/book.do?bookid=BOK00010503270AL"
    }
  ];

        $scope.searchBookList = function(currentPage) {
          if (angular.isUndefined(vm.searchQuery)) {
            toastr.error('Please fill out the form.', 'Error!');
            return;
          }
          var dataObject = {
            query: vm.searchQuery,
            target: (angular.isUndefined(vm.standardItem.selected)) ? "" : vm.standardItem.selected.value,
            page: currentPage
          };
          console.log(dataObject);
          MessageService.interactWithServer('/search/book', dataObject).success(function(data, status) {
            if (data) {
              console.log(data);
              $scope.bookListTableData = data.documents;
              $scope.hasPrev = (currentPage == 1) ? false : true;
              $scope.hasNext = !data.meta._end;
              $scope.currentPage = currentPage;
            } else {
              $log.warn(data);
            }
          }).error(function(data, status, headers, config) {
            $log.error(status);
          });
        };
        

        $scope.searchBookListNext = function(nextPageNum) {
          var dataObject = {
            query: vm.searchQuery,
            target: (angular.isUndefined(vm.standardItem.selected)) ? "" : vm.standardItem.selected.value,
            page: nextPageNum
          };
          console.log(dataObject);
            MessageService.interactWithServer('/search/book', dataObject).success(function(data, status) {
                if (data) {
                    console.log(data);
                  $scope.bookListTableData = data.documents;
                  $scope.hasPrev = (nextPageNum == 1) ? false : true;
                  $scope.hasNext = !data.meta._end;
                  $scope.currentPage = nextPageNum;
                  console.log($scope.currentPage);
                } else {
                    $log.warn(data);
                }
            }).error(function(data, status, headers, config) {
                $log.error(status);
            });
        };

        $scope.open = function (page, size, book) {
          var modal = $uibModal.open({
            animation: true,
            templateUrl: page,
            controller : 'searchPageCtrl',
            size: size,
            resolve: {
              items: function () {
                console.log(book);
                return book;
              }
            }
          }).result.then(function(items) {
              console.log("get", data);
              MessageService.interactWithServer('/bookmark/add', items).success(function(data, status) {
                  if (data) {
                      console.log(data);
                      toastr.success("SAVED!");
                  } else {
                      $log.warn(data);
                      toastr.error("FAIL!");
                  }
              }).error(function(data, status, headers, config) {
                  $log.error(status);
                  toastr.error("ERROR!");
              });
          });
        };

    }

})();