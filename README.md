# Lab 10 — Dataflow Testing

## A хэсэг. gcd() функцийн Definition-Use анализ

### A1. Definition ба Use

`DataFlowTargets.java` файлын `gcd()` функцэд дараах тодорхойлолт (Definition) ба ашиглалт (Use) байна.

| Хувьсагч | Definition | Use | Use төрөл |
|---|---:|---:|---|
| x | 13 | 16 | c-use |
| x | 13 | 20 | c-use |
| x | 17 | 16 | c-use |
| x | 17 | 20 | c-use |
| y | 13 | 15 (True) | p-use |
| y | 13 | 15 (False) | p-use |
| y | 13 | 16 | c-use |
| y | 13 | 17 | c-use |
| y | 18 | 15 (True) | p-use |
| y | 18 | 15 (False) | p-use |
| y | 18 | 16 | c-use |
| y | 18 | 17 | c-use |
| tmp | 16 | 18 | c-use |

- c-use: хувьсагчийг тооцоолол эсвэл утга буцаахад ашиглах.
- p-use: хувьсагчийг нөхцөл шалгахад ашиглах.
- True/False: `while (y != 0)` нөхцөлийн үр дүн.

### A2. All-DU-pairs тестийн хүснэгт

| № | Хувьсагч | Def мөр | Use мөр | Төрөл | Def-clear зам | Тестийн ID |
|---:|---|---:|---:|---|---|---|
| 1 | x | 13 | 16 | c-use | 13-14-15(T)-16 | gcd_12_8_covers_loop_iteration_pairs |
| 2 | x | 13 | 20 | c-use | 13-14-15(F)-20 | gcd_7_0_covers_zero_iteration_pairs |
| 3 | x | 17 | 16 | c-use | 17-18-15(T)-16 | gcd_12_8_covers_loop_iteration_pairs |
| 4 | x | 17 | 20 | c-use | 17-18-15(F)-20 | gcd_12_8_covers_loop_iteration_pairs |
| 5 | y | 13 | 15 (T) | p-use | 13-14-15(T) | gcd_12_8_covers_loop_iteration_pairs |
| 6 | y | 13 | 15 (F) | p-use | 13-14-15(F) | gcd_7_0_covers_zero_iteration_pairs |
| 7 | y | 13 | 16 | c-use | 13-14-15(T)-16 | gcd_12_8_covers_loop_iteration_pairs |
| 8 | y | 13 | 17 | c-use | 13-14-15(T)-16-17 | gcd_12_8_covers_loop_iteration_pairs |
| 9 | y | 18 | 15 (T) | p-use | 18-19-15(T) | gcd_12_8_covers_loop_iteration_pairs |
| 10 | y | 18 | 15 (F) | p-use | 18-19-15(F) | gcd_12_8_covers_loop_iteration_pairs |
| 11 | y | 18 | 16 | c-use | 18-19-15(T)-16 | gcd_12_8_covers_loop_iteration_pairs |
| 12 | y | 18 | 17 | c-use | 18-19-15(T)-16-17 | gcd_12_8_covers_loop_iteration_pairs |
| 13 | tmp | 16 | 18 | c-use | 16-17-18 | gcd_12_8_covers_loop_iteration_pairs |

### A3. Тестийн тайлбар

**`gcd_7_0_covers_zero_iteration_pairs`**

`gcd(7, 0)` үед `while (y != 0)` нөхцөл анхнаасаа False болно. Давталт ажиллахгүй тул x-ийн 13-р мөрийн тодорхойлолт 20-р мөрийн `return x` дээр ашиглагдана. Мөн y-ийн 13-р мөрийн тодорхойлолт 15-р мөрийн False нөхцөлд ашиглагдана. Энэ нь давталтгүй замыг шалгах тест юм.

**`gcd_12_8_covers_loop_iteration_pairs`**

`gcd(12, 8)` үед Евклидийн алгоритм хоёр удаа давтагдана:

1. `x=12`, `y=8` → `tmp=4`, `x=8`, `y=4`.
2. `x=8`, `y=4` → `tmp=0`, `x=4`, `y=0`.
3. `while` нөхцөл False болж, `return 4` буцаана.

Энэ тест нь давталтын эхний болон дараагийн мөчлөгт үүсэх DU хосуудыг шалгана.

### A4. Туршилтын үр дүн

Команд: `mvn test`

- Нийт тест: 2
- Амжилттай: 2
- Алдаа: 0
- Алгассан: 0
- Үр дүн: BUILD SUCCESS

Тестийн гаралтыг `/tmp/lab10-gcd-test.txt` файлд хадгалсан.

## B хэсэг. bonus() функцийн Dataflow анализ

### B1. Definition ба Use

| Хувьсагч | Definition | Use |
|---|---|---|
| attendance | 31 | 32 (p-use), 38 (p-use) |
| threshold | 31 | 32 (p-use), 50 (p-use) |
| present | 35, 40 | 40 (c-use), 50 (p-use) |
| streak | 36, 41, 46 | 41 (c-use), 42 (p-use), 43 (c-use) |
| best | 37, 43 | 42 (p-use), 53 (p-use) |
| a | 38 | 39 (p-use) |
| score | 49, 51, 54 | 54 (c-use), 56 (c-use) |

### B2. All-defs тестүүд

| Тестийн нэр | Оролт | Шалгах зорилго |
|---|---|---|
| nullAttendance_coversParameterDefinition | null, 0 | attendance параметрийн definition |
| negativeThreshold_coversThresholdDefinition | [1], -1 | threshold параметрийн definition |
| mixedAttendance_coversLoopDefinitions | [1,1,0,1], 3 | present, streak, best, a, score |
| longStreak_coversScoreAdditionDefinition | [1,1,1,1,1], 6 | score-ийн 49-р мөрийн definition-ээс 54-р мөр хүртэл |
| onePresentDay_coversScoreReturnDefinition | [1], 2 | score-ийн 49-р мөрийн definition-ээс 56-р мөр хүртэл |

### B3. All-defs үр дүн

`mvn verify` командын үр дүн:

- Нийт тест: 7
- Амжилттай: 7
- Failure: 0
- Error: 0
- Skipped: 0
- Build: SUCCESS

JaCoCo тайлан:

- Branch covered: 16
- Branch missed: 0
- Branch coverage: 100%
- Line covered: 23
- Line missed: 1
- Line coverage: 95.83%

JaCoCo-ийн branch coverage болон DU-pair coverage нь өөр хэмжүүр юм. Branch coverage 100% болсон нь бүх feasible DU хос хамрагдсан гэсэн үг биш.

Үр дүнгийн файлууд:
- `results/mvn-verify-alldefs.txt`
- `results/jacoco-alldefs.csv`
