package pub.utils;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 13-5-11
 */
public class WbUtils {

    // unicode 中汉字的范围[\u4e00 - \u9fa5]
    private static final int HANZI_START = 19968;
    private static final int HANZI_COUNT = 20902;

    private static String firstLetterArray =
            "gsgahgddddhggigggnvfegagrgowaxbgugtgggufznwuzknednkkjozuvmyyfgizyyqenqnttytpqtttqtrrtrtttnnnnvtbnxmx" +
                    "gtnuchnladqtntuerugibfryiffeqfeofhbqcqggffgfffgfgffmggghgcgbzyyyuyyuyyyyyyyyyyuyyyyuyywzwwwwwwwwdwww" +
                    "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwnwhwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww" +
                    "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwswwwwwwwww" +
                    "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww" +
                    "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwfwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww" +
                    "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww" +
                    "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwgwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww" +
                    "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwqgcafkyiqtiwdwqtdhuvaqmuufdidqddtudddittmwgwwwuwuuaguirahmuwuuuuuuuzm" +
                    "mmmdmmmmmgmmjmjfjjzppppppppppppppppppppztduuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuummthmntm" +
                    "mmmqmwmmmmmwuzqfhmbfibbovnzvvvwaqtftfqqrmgafnvegymmwxpevmnwcukffqtmkgctggffdebrnuqgyfwgmmmyuggymkwid" +
                    "guqukwatjduaaugmpxdcyoewyvdndtuymnmgtwpamwxugnfmcsavcwdvtlyhnqwkhwwiaywtwrqtyxnllclaltdirylrfevfqvrd" +
                    "rdcaiurufucafgyrdgtftgyccqdkuyfubsaxdtjjacakweoansfgaxvrqattuuhylatthdtyazqqqqqqqqqqqqqqqqqqqqqdqqqq" +
                    "qxwuxjzaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaafntagvttfufgnwfnryhfuffiafxhgykhhhhhhfahzbbqarqqqvvfqqu" +
                    "rtbwgrkqswdqdddddddddddddddddddddddddddddddddddddddddddddddiddcdcvdfccnegcccccffccedcrnntvcghcbeywnu" +
                    "pfnvchokdqkkkkkkvkksckkdvakknkkkkkbkkkyktkkkwfkkmqrgktkkkkkkkkkvkygkkknkkkgkkkkwkkkykkkkkkkkkkkkkkgr" +
                    "kkkkekkikktkkkkkktkkkkkkkkkkkkkkkkkkkkkkmkkkkkkkhkkkkkkkkkkkkwkkkkkkkukkkkkktktkkkkkkkkkkkkkkkkkkkkk" +
                    "kkkkukknkkkkkkkkkkkkdkkkmkkkykkkkkkkkfkkkkkkkkkkkkkkkkkkkkkkkkkkkskkkkkkkkkkkkrkkkkkkkkkkkklkkkkkkkd" +
                    "kkkkkkkkykkkkkkkdkkkgkkfkkkkkkkkkkkkkkwkkkkkkkkkkkkkkkkkkkkkkkuukkkkkktukkkyykkkkhkkkkkyrkkkkkkkkkkk" +
                    "fkkkkkkkkkkkkkkukkkkkkkkukfkkkkkkkkkkkkkkkkkkkkkfkkkkkkkkkkkkkfktkkkkkkkkkikgkkkkkikfkkkkkkfkkkkkkkk" +
                    "kkkkkkkkkkkkkkkkekkkkkklkkkkkfkkkkkkkkkkgkkkkkkkkkkkkkkkkfkkkkkdkkkkkkkikkkkkkkkkkkkkkkkkkkkkkkkkkkk" +
                    "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkekkkkkkkkkykkkkkkkkkgkkkkkkkkkkkukkkkkkkfkkkkkkkkkkkkkkkkkkkkkkkkkk" +
                    "kkkkkkfkkkgkkkkkkkkkkfxkkkywkkkkkkkkkkkkkkkkkkkkkkgkkfkakkkkkkkzmlllllltlllllllllltlmlllltllllllllll" +
                    "lllllllllclllllllllllllllllllllllllffffcfffddfffffffftffffdfffffjfffffffffffffffwfffwfxfirfffgjfffff" +
                    "bffifffffffffffffffffffffffffffffftfdfffwffgffffufcfsrfffffffffffwffffvffgfrfffffffffffffffftifftfff" +
                    "fbffffmffffffrffffffffffsfffffffffffffffffffffffffffffafffbiffiyfafaffgffffsflfffbfffffffffffwfffcff" +
                    "ffffffffffvffffffffiffffflffffffvffofefffufffffiffffffpafffiffbfffffffffiyfffyffflyfffyffffffjffffff" +
                    "frfnfffaffffffffbffffffffffflfyfbfbnffffffffffffgfeffnnfsywffffffiffghfdfffflffffffufhfffffflffftfun" +
                    "ffffffffffffffffffzetttttyqcftwdqodvuqqqqmqvyqqqqqayqjadggdfntmddrdduddgdgddddddgdqdddddddfdhuddqddd" +
                    "dyudnceddddduddgdtctudddndddlxdvvvvvvvvvvvvvnvvvyvuvvvvvvvvvvvvvvvvvvvvbvnvjvvvvvevvvvvvvvvvvvvvqvvv" +
                    "vvvgvvulvvvvvvvwvvvvvvvvvvvvthvvvvvvuvvvvvvvvvvvvvvvvlvvvvvvgvvvvvvvvvvuvdvvovvvyvvvvvrvvivvvvvvvvvv" +
                    "vvvvvvvvnvvvvvvvvvvvvvvvvvbvvvvvvvvbvlvvvvivvvvvvvvvvvvdvvvvvvvvavvvvvvvvvvvvvsvvvvgvxvvmvvvvvcvvvvv" +
                    "vvvvvvvvvvvvvvvvavvvvvvvvvvvvtvvvvvvvvvvvvvvvevvvvvvvevvvvvtvvvvvvvvvvvvovvvvvvvvgvvvvvvvvvavvvvfvfv" +
                    "vvvvvvvvvvvgvvvuvvvvvvvvvvvvvvvevvvvvvavvvvvvvvvvvnvvvvvuvvvvvvvvvvvvvvvvvdvmilvyvvvvvvvvvvvvfvvvvvv" +
                    "vvvvxvvvbbbbbebpdbefbfbbnbbtbvixbbybgbbaynbubqyfwnbbbabxzppppppppppppppppppppppppppppppppppppppppppp" +
                    "ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppfcfvneydafggtdungnuvfoui" +
                    "iiwtqeihiiiiiidjaizwddwadwdgdfdddyddddgdnvnnnnnnnnnnnnnnnnnnnnnnnnnnrnnnnnnnnnnnnnpnnnnnnnnnnnzgumtt" +
                    "mmmmmmmmmmmmmmmmmmmmmmcmmmmmmmmmwmmmmtmqmmmmmmmmmmmmmmmmmmmmmwmrmmmmmmmmmmmmmmmimmmmmmwmmmmmmmmmmmmm" +
                    "mmmmmmmmmmmmmmymmmmmmmmmmqmmmmwmmwmmmmimmmmmmmmmpmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm" +
                    "mmmmmmmmmmttmmmmmcmmmmkmmmmmmmmmmmwmmmmmommmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmgmmmmmmmmmmmmmmmmmmmmmmmmbm" +
                    "mmmmmmmmwmmmmmmmmmmmtmmmmmmmmmmmmmmmmmmmmmmmmmmmmmxmmmmmmmmvbkyygvvivadaaaaaaaucgnnncrvaabnucnmggtyd" +
                    "mjmvjmmqqymmmvwmmmmmpmvrmumyvmmuvwgmmmmwvydgymmmmmamimmmmmmmmmpmmmmfmmmtmmmmetmmmammmmdmmmmmmmmuimnm" +
                    "mmmfmmmmmmfgrfutffxxxxxyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy" +
                    "yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyzvttmrvlsmzagcnygwfwynuaaaaagqqxxxxxxxuxxxxuxxxxxxxxxvxxxxuy" +
                    "xxxxxxxxxxxxxxxpfxoxxxuxxxxxxxxxzxjixvxdxxvvxxvvvzgymyuggehmsfpsujgttttttttttttttttttttttttttttttttt" +
                    "tttttttttttttttttttttttttttttttttttttttttttttttnznnnfnnnnvtnhgannnnfynnnygynkngnnrnnnwnnnnnnnnnnnwnn" +
                    "nnnnnqnwndwnnnnnnnnnnntnnnvnnnnndnnnnlnncnnrwqnnqnnnnnnnnqnnnnnfswnucnnnnwnnnnnnnnnynnnvannnnvtnnufn" +
                    "ndnnnnnunbndnlnnnantnnnvyggwnnnnnnncnnnnnnwnntrnnnnnqnnnnnnnannnnnnnwtnktynnwngfennnnndfnnunnpnnnnnn" +
                    "nsnnhnnnninnpnannatnnnjnnnnnnnnnggntnvnnntftnnnnnnnsnnndnannnnnnntfnnntnwnnnnnnunnnnnnncnpjennndnnnn" +
                    "nnnnfnnnunnnnnnhnnnnsnnbnnndnlinnnnrunncnnnnannnnangnlnntannnnnnfnfdnnntnghnnnnnnnynafnndnwunnddttnt" +
                    "nnwuqnnnnunnnnssnfnnnynndnnnnonnntnnnnnnnnpnnnnnnnnnannnnnnannnenynsnnnnninnndqninnnnnnonnninnnnnnnn" +
                    "ynngnntnnnnnhnnnnnnfxannnuuuaadgddacdtaaanawhmddamdfuakwejiwsgfdgpnhkhhnfhrygyyyayyyryyyyyyyyyyrzfrr" +
                    "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrbrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                    "rrrrrrrrrrrrrrrrrrrrrrrrrrrvrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrraurrrrrrrrrrrwrrrrrrrrdrrrrrrrvrrrrrrr" +
                    "riryrrrrrrrrrrrrrrrrrrrrrrirrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                    "rrrrrrrrrrrrrrrrirrrrrrrarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrdrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                    "rrrrrrrgrrrtrrrrrirrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrtrrrrrrrrprrrrrrwrrrrrrrr" +
                    "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrryrrrrgffrrrrrrrrarrrrrrfrrlrrurryrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                    "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrlrrrarrrrrrrrrnrrrrrrrrrrrrarwrrdrrrrrrrrrrrrrrrrrrrrrsrrrrrrrrrrr" +
                    "rrrrrrrrrrrrrrrrrrrrrrrxrrrrrrrrrrrffudfhzngwnnafwygrhqncdwguohttwqtdfwwgggmwfuwwuimcjnajoyduicrajtn" +
                    "omymgubglhaqftutllwufwwyyyyyyyyydgyyyyyuqocqwkvaffalrrrwnlqdloaaufnaoqrxnyyyyyyyyuyyyyyyygyyyyyyyyyy" +
                    "yyyyyyyyyyyyfavrmjjhxjjjqvvjjjjjjjjjjbjjjjgjjjjjjjjjjjjtjjjqjjjjajjjjjdjjtjjjjjjjdjjjjjjnjjjjjjjjjyj" +
                    "jjjjnjjjjjjjjjjjjgjgjjjjjjjjjjjjjjjjjvjjjjrjjjjjjjjjjjujjjjsjyjjjjtjjjjjjjljjjjjjjjnjjjjjjjcjjjjjjjj" +
                    "ujjjjjjjjjdjvjjlfjajjjjjjjjjjjjjujjjjrjjjjdjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjxjjjjjjmjgjqjvggjjuuf" +
                    "jajwjfuweeeeeeeeemeeueeyelyyefaaeeaeemeessfgssfssrsssmessssssssgqsvssssssssssssssssssssynssssssgstss" +
                    "sgsssssssssssgjssssssssssssswsssssssxsssssssssssssssssssjssssssgsssssssssqsssscssslsssssafssssssssss" +
                    "ssswssssasiicsssssssssssssssssssssssgssssssshsssssssssssssssisgsssssssssssssdsssssssssgswssssussssss" +
                    "sssssssssssssssssfysqssssssvpsushsssscfsssssssssssssssssssssusssissssssssssssssssssssissssssssrsssss" +
                    "ssssssssssssswswssssspsstsssssssssssssshssssssssssstysssssasssssdssssssggsssssssisssssssysssssssssss" +
                    "ssssbssasssssssssssssysssssssssssssssssssfsslsssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
                    "cssssssssssssssssssssossssssssussssssrssssssssssssssssssssssssfstsssssssssssssfsssssssossssssssssses" +
                    "ssssysstssssssusssssskssssfsssssssssssssssslsrsssssssssnsssfssssfsssssxssssssssssssssssssswsssssssss" +
                    "ssssssssssssssssssssssssssssssssssssssrsssssssssgsssssssssssdsssssssnqsssssssssssssssssssssssssssssb" +
                    "ssssssssssssssssslsnssssssssasssssssssssssssssssssssssssssodsssssssssssssssslsssssssssssgsssssssssss" +
                    "sssesssssbsssssssssssbssssssssssssssssssssxsssssssssssssqucrgjwaqvbryluftwwhyxgqcdaocqfqsamtmsujwuyr" +
                    "swaaahurhffvowuwlljwcahghhghhhghhhhhvhhhdhhdwghggggggggggggggggggggggggggggggggggggggggggggggggggggg" +
                    "gggmawyrfaqffqfnvvfyluavqafxxxttfxgtxxxllxqtttxilttnwtbttttytgrtttcttctjtwtdjtjcltfucittyktathlqqnqy" +
                    "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrizbyyniiiwiiiifiiitiiiiiiiiiiiiiiimiiiiiiiaiiiiiiiiiiiiifiiiiiiiiiii" +
                    "iiiiiiiiiiiiiiiiiipiiiiiiiiiiiigiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiriiiiiiiiiiiiiiiiii" +
                    "iiiiiiiiiiiiiiiiiiiidiiiidiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiitiiiiiiiiiiiiiiiiiidiiiiiiiiiiiiiiii" +
                    "iiiiiiuiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii" +
                    "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiqiiiiiiiii" +
                    "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii" +
                    "tiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiioi" +
                    "iiiiieiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiifiiiiiiiiiiiiiiifiiiiiiiiiiiiiiiiiiiiifiiiiiiiiiiiii" +
                    "iiiiiiiiiiinixiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiieiiiiiiiiiiiiiiiiiiii" +
                    "iiiiiiiiiiiiiiiiiwiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiitiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii" +
                    "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiitiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiixiiiiiiiiiiii" +
                    "iiiiiriiiiiozgoodooqovooqoofovpooaowojoooooooooooooooooaoqjoooooooooooooooooomooqcooooooohyooooooooo" +
                    "oooogqowoogwooooodfooooooboooooooooooooiorgooorooooooyoooooookwovoooygonoorboooooooodosuooooorobyowo" +
                    "ooooooooooooooqojoooojoooooooooooooooouoofoooaoooojhooqooooooojjooooovfooowoooooooooooooooocooootocc" +
                    "oootoooooooooaoooooyoooooootnooogdooofxoooooofooooooooooqfooooooooooooosoaoooionoowooowooooooooooooo" +
                    "oooooooooeoooofooowooooaooooooooooqoooooooooooooooyoooooworerereeeeelewwwwwqqdgnnnnnnnnttttttttttttt" +
                    "tttttairtttcttptttttttttttwttttttduttttttyttnttttttrttttttftttttttotytnftttxttttttttwttwdzdqqqqqqquq" +
                    "qqqqqqqqnqqqqqqqqqhqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqdqqqqdsqqqqqjqqqqqqqqq" +
                    "qqqqqqqqqqqqqqqqqqfqqqqqqqquqqqqqqqqqqqmqqqqqqqqqqnqqqgqqqqquqqqqqqqqqqiqqqqqqqqqqqqqqqqqqqqkqqhqqqq" +
                    "qqqqyyyyyggggtggggggggggggggggggggggggggggggggggggggggggggqggggggggggggggggggggggggggggggggggggggggg" +
                    "ggggggggggggggggggggggggggggggggggggggggggggggggggggggggggjggggggggggggggggggggggggggggggggggggggggg" +
                    "gggggggggggggggggggggggggggggggggoggigggggggfggggggggggagggggggggggggggggggggggigggggggggggggggngggg" +
                    "ggggggggggggggwggggggggggggggggggggggggggxggggggrrrrdfsuyrggaggwypwagggdwcuugkdqgituttytsguotuluaagf" +
                    "ournqymhaaaathitjtyuetgteetgcgqplmljjjtlqmlgllvllmljijylllllllllgoyilllylqclyyflrljwlllloltvwllllgwl" +
                    "lvilllgllllxvlllllxlgllngnnnfxzuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu" +
                    "uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu" +
                    "uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuwwwwwwwrdrrrrrrrxrrrrrrrrrrrrrrdrrrsrrrrrr" +
                    "rrrrrrrrrrrrrrhhhqpfcasfphqhfyflheggxkwdetudqfmgfjwidiuutgfdivajxvlatqwhefiwaxhhhhyhfhhhshhhhhrhhihh" +
                    "hhhhhnhrhhhhhhhhlhhhdhthhhxfhhqhhhhhhhhhhhhhhhhhhhhuhhhhhhlhuhwhhhhhhhhhhhhhhhhhhhhhlhhhhthhhhhhhhhh" +
                    "hhlhhhhnhhhhhhhhhhhhhhthchrhhhhhhfhhhhhlhhhhhhahhhhhhhhhhhahhuhhhhhhhhhhhhhhhhhhhhhhhfhhhhhhhhhhhhhh" +
                    "hhhhhhhhhxhfhhhccccccctcxtqttttttttttttdddddddddddddddddddddddddddddddddddddddddddddddddddhdddddddvd" +
                    "dddddddddddddddddddddddddddddddddddddddddddddddddddddddddrddddddiddddddddddadddddaddddiddddddddddddd" +
                    "ddddddddddkddddddddgddddddddddddddddddddddddddddddddddddddddtddddddddddlddddddddddddydddfddddddddddd" +
                    "ddddddddddddddddddldddddwddddnddddddwdddddddddddldddsdddduddddddddfzppppppppppppppppppppppppppppfppp" +
                    "pppbphppppppsppppwppppppppppppppppppysppppppppppppppppppppppppppoppppppppptpppppppppppppppppmtjyhwtt" +
                    "ttttttttttttotttttttttttttttttttttttttdtttttttttttttttuttttttttttytttttttttttttttttttttttttttttytttq" +
                    "ttttttttttttttttttttttttttytftttttttytttqtxtttttttttttttttttttttttttttttttttttttpppppppppppppppppppp" +
                    "pppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppuuuuuuuuuuujuuuuuuuuuuuuuuuuuuuau" +
                    "uuuuuuuuuuuuuttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt" +
                    "tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt" +
                    "tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt" +
                    "tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttotooooooo" +
                    "ooooooqoooooooxoooooooooooooooovboosoouotxoooooooooooihootooooooooooooooooooooooooooooofooooooooyooo" +
                    "oooooooooonoooooobootaboxxxtxxxxxxxxxxxxxxyxxxxxxxxxxxxxxxxxxxxxgxfxxrxjxxxhxxslxxxxxxxxxxxxxxxxxxxx" +
                    "xxxxxxxxxxxxxxxxxxxxxxxwdxxxxxxxxxxxxxxcxuvxxxxxxxxrxxxxxxxxxxxxxxxxxxxxxxxxxxxxdxxxxxxxxxxxxxxxvxax" +
                    "xxxxxxyxxxxxxxxxxxxxxxxxxxxxxxxxxxaxxxxxtxxxxxxxxxxxrxxxxxxxxxxxxxxxxxxxxxxyxxxxxxxxxxxxxxxxxxxxoxxx" +
                    "xxxtxxxxxxxxxxxxxxxxfxehxxxxxxxxxxxxxxxxxxfxxxxyxxxxxtxxaxxexxxlxxxxxxxxxxxxxxxgxxxxnxxxaxxxxxxlxaxx" +
                    "xxxxnxxxxxxxxxxxxxtxxxxoxxxxxlxxxxxxxxxxxxxgxxxzxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                    "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxrr" +
                    "rrrrrrrrrrmofrrrrrlymlrrrmlmmplllpllllllllllllllllllllllllllllllllllllllmllllllllluguduuguuuuuuueuuu" +
                    "uuuuuuuvvuuuuuuuuuuuuuuuuuuuyuuunnnnnwndnfnnlsunnnwnnnnfuwwqamnwnpnnndmnkfupnynnnngnfrsannroffttnhlf" +
                    "ifffffffffffdddddmdddddddddddddddddddddddddddddddddbbbbdwbbbbbbbbbbbbbbbbbbdbbbbbbbabbbbbbbbbbutbbbb" +
                    "bbbbbbbbbbbbbgftbbbbbbbinbuvzygvxvdyymeeeepteeeyeeieekeeeeeeeeeeeemeeyeeeeeheeyeqeeeeeeheejeeeelmeee" +
                    "eemeueeemeeeheeeeeeeeeeeedeetneeeeeeveeeeeeeeeeqeeeeecfebmeeeleeeeiveeeeeeeeyeeeeeeeeeeeeeedeeemewee" +
                    "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeaeyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeyeeeeeeeeoeeey" +
                    "yeeeeeeeeeheeeeeeeeeegemeeeeeeeeeeeeeeeeeeyeeeeeneneeeeeeeeneeeeeeeeeeweeeeeeyeexaeaaandantttttttttg" +
                    "gggfggfgvqvtevdvvvwwwwawtwwtttwtttwwwttqenrttttttttttttttttttttttttttttttttttttttttttttttttttttttttt" +
                    "ttttttttttttttttttttttvycaqdxumdbzaaaaaaaaaaaaaagaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaq" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaagaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaoaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaagaaaaaaaa" +
                    "agaaaaaaaaaaazhhhhrvhhhhhhhhhhhkuheghhahhihjjjjjjnjjjjjjjjgjjkjdqjjxjjjjjyjjjjjjjjjjjgjjjjjjjjjjwjjj" +
                    "cjjjjbjjjjjjjjjjjjjjjjjsjjjjjjjjjjjjjjjnjijjjjjfjjjwjjjjjjjjjjjjjjjjjadjajyjrjjjjjjjjjjjjjjjljjdjjjr" +
                    "jjjjjjjjjtjjjjjjjjdjpajjjjjjsjjjjjjjjjjjejjjjjjjajjjjjjjjgjjjgjjjjjjjjjjjjjjjwjjjjjjjjjjjjjjjcjjnjjj" +
                    "jjjjjyjjjtjjjjjjjjjjujjjjjjjjjjjtgjjjjfjjjjjjscjfjjjjyoeyjjjjjjfjjjgjnjjbjaywjjjjtjnjnjjfjjajjcjjjjj" +
                    "jjjjjjjgjjfjjjujjjjjjjjjdjjjjjjjjjjjajjjeqjjajjljxjyjjajmjjjjjjjjjgjjjjjjjajsjtjdxdjubjfjjuejtjyjjuj" +
                    "jjajjggxjijvtytttttttttvtttttttttttttttttttttttyzpppgpppppupyppppppyppypppwppfpapqpplppwyppppppppppp" +
                    "ppppppupypppypppppppydppxpyppppppppppppppfgppuppppwppppyppypypppfprypnpivpppppyppppppppppppidypppyph" +
                    "prpppjppppppppppppyyppypppppppppepppppyppppbptpppyoppyppppppppfypnpppppyypppppppppppppppppppppppppnp" +
                    "ppppppppppppppppppupppppppppppgsssssssssssshcihfhttegnpnhqirifhfvaothmwakfuumpofhlarwuehuhwhgapfamcm" +
                    "fephjimafmwfahqqqtqqqqqqhqqquqdqqqqqqqqqqqqqqdqfqqqwqqxqqqqqyyyyvyyqyyyyyyyyyyyymyyyyyuyyyyyyyyyyyyy" +
                    "yyyyyyyyyyyyyyyyyyyyyyhyyyyyyyyylyyyyyyyyyyyyyyyyyyyyyydyyyyyyywyyyyyyyyyyyyyyyyyqyyyyyyyyyyyyyyyiuy" +
                    "yyyyyyyryyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyuyyyyyyayyyyyyyyyywyyyyyyyyyyyyyyyyyyyyyyyyyyytyyy" +
                    "yyyyyyyyyyyyyyyyyyyyeyypjyyyyoyyyyyyyyyyyyyyyyyyyyyyyyfyyayyyyyyyyyyyyygyyfyyxyyyyyyyyyyyyyyyyyyyyyy" +
                    "yyyyyyyyyyyyyyyyyrayyyyynyyyyuyyyyyyyyymawyyyyyyyytyyyxuyywtwyyyyyyyyyyyyyyyzyyyyyyyyyyyyyyyyyyyyyyy" +
                    "yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy" +
                    "yyyyyyyyyyyyyyyyyyyyyyyyyyywtwwwwwwewpwwwwggmgmwggagdmdddeegeueeeeeeequeeeheeeyceeeefeeeeeeeeeeeeeee" +
                    "eeeeeeeeeeeeeeeeeeeeeeeeeeeeemhcqmaammmwwmwxgrgmamhakcmlwmmxmmmqlfmwmmmusmmmymmmmmmppmmmmmsfmmimmyaf" +
                    "mmmmpmrfmmvmmmwmgmmfemmmmpamammmfgmymmtdmmmymuammqmdmhmummhqcamgjmmwrmwwmmmxamfamkmwqxlmmrsmhwmmummm" +
                    "mgmmmfmimmmymmgmgmmpadtymmyuffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff" +
                    "fffffffkkkkkdkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkakkkkkkkkkkkkkkkkkkwkkkkkkrkkkkkkkkkk" +
                    "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkpkkkkkkkkkkkklkkkkdkkkkkkkkkkkkkkkukkkkkkkkkk" +
                    "kykdkkkkkkkkkkkknkkkkakkkkkkkkkkkkktkkktkkkkakkkkkkkkkktttttttttttttttttttttttttttttttlllpglllllllll" +
                    "llllllllllllllllllllclllulllllllllllllllllallllllflmlllllllllllllllllillllllllflldllllllllllllllllll" +
                    "lllwllfllllllllllllllllllllllllllwlllxlllllllllllllllllllllllllllflllllllllfdillllllllllxlllllludctn" +
                    "tuguebuuuuhuueuuddmskezgelvftbdmftgftnbfdnggegqdfryartdggykffflnhnvatmljcqmryrtqspfclmdouysqfwgqvuti" +
                    "trufttbghistefurwgqgcycqgrkgtctlimgwsdactpvvrmwyhftjqfflgawjdruumnujjhypkymjnruufnfkfqvqblrnfujkcexu" +
                    "gucgggyealnaoufsnckkdhtnraqplywgettvttqlgkscnvmfgyvaqyfywygvrpdfgoadqqmacrnggvugqqowafrfddywqgfqrfut" +
                    "ftygwuyptdgqkggetfydgvkfwmgkuqgrgymobusvstuxuhqhnfanaukwstpwwxxvyptdygqtwxkyykavynagfwgujwrwuvxueuqu" +
                    "otkxokwmabyffydaaqtfamdtgssussssssiwssssssssssssssssssssfssssssssussssssssssssssssssssssssssssssssss" +
                    "tssssssssssossssussssssansssssssssssssssssssswsssstettttjtjjfqqqqqqqqqqqwqqqqwqqqqqqqqqqqqqqqqqqqqqq" +
                    "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqhqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                    "qqqqqqqqqqqqqqqqqqqqqqqqjqqqqqqqqqqqqqqqqqqqqqqqqqaqgqqqqqqqqqqqtqqqqqqqqqqqqqqqqqyqqqqqrqqqqqnqqqqq" +
                    "qqqqqqfqiqqqqqqqqqqqqqqqqqwqqqqqqqqqqqqqqqqtrqqqqqqqqqqqqqqaqqqqqqqiqtqqqqqqqqqqqqqqqqhqqqqqqqqqqqqq" +
                    "qqqqqqqqqqqqqqqqqqqqqqqqqqlqqqqqqqqqqqqqiqqqqqqqqqqqqiqqqqqqqqqqqqqqqqctqqqqqqqaqqqqqqqqqqqqqqqqqqqq" +
                    "qqqqqqqiqqqqqqqqqqqqtqqqqqqoqwqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqgqqqqqqqqqqqyqqqqqqqqqqqqqqqqflqqq" +
                    "qqqqqqqqqqqqqqqqqqqqqqqqqubqqqqqqqqqqqqqqqqqqqqqyqqqqqsqqqqqqqqqqqqqqqqqqqqqqqqqqqnqqqqqqqqqqqqlqmqq" +
                    "qqaqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqxoqqqqqzqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                    "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                    "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqddddd" +
                    "dddtuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuquuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu" +
                    "uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuwzbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" +
                    "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" +
                    "bbbbbbbbbbbbbbvfswpwwwcaidwgdawygtqwhyeqhvtwwmkfmwaqyvebwnyawwwwffffffffffffffffffffffffffffffffffff" +
                    "fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffgggggugggggggddtydddddddaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaapaaaaaaaaaawaaaaaaaaaaaafaaaaacaaaaaaaaaaatgaaaaaaaaaaaaaaaaaaannnnuninf" +
                    "nnnnnjnnnnnnnnnnnnffffjffdafhwyuuuuuuuuuuwfxudsxvakfehgdwfrycfwgmhuhwgcdwuptqftaahywnuoiggiqdetfxvtw" +
                    "gtthgteewpsobyjyxqwljpkypdjmujtmdcxffmonsuadfjwydwhyfapjhhobadsxfakegfdgrwwychwhcfgtwxxyahitwxxjjjkm" +
                    "upbafcjoyfhammgmmmmummmmmmmmmmmemmmcmnmmsmdmmmomammmmmummmemsdmntnnwwuwwwwwqxwwwuwwwwgwwwwwwkwhwwwww" +
                    "wwwwwwwwuwuuwdwwhwwwwwwwwwwwwwwwwwwwwdwwwwwwwwgwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwnwwwwykwxwwwwdwwwwwwz" +
                    "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqquvutttttttttttttttftttccucccccccccccccccccccxccccc" +
                    "ccccqccccvccclccccccccccckccccuccccccccccccccccccccccccccccccccccccccccccccccccycccccccccccccccccccp" +
                    "cbccecccccccccfcccccagcccccfccccccccccccccccycaccccccucccccccccccccccccccccccccccvlccckccccccccccccc" +
                    "ccccccccbcccgcpcccccccccmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmyyyyyyyddddddddddddddddddddd" +
                    "dddddddddddddddddddddddddddddddddddddddddddddddddhhhhhhhhhhqssghgafggfgxrrrrrrfrrrrdrrrrrrrtrrrryrrf" +
                    "diqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqhqqqqqqqqqqqqqqqqqqqqqquqqqqqqgqqqqqqqqqqqqqqqqqqqqquq" +
                    "qqqqqqqqqqqqwqiqqqqqqqqqqqquqqqqqqqqqqqqqqqqqqqqtqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                    "qqqqqqqeqqqqqqqqqqgqqtqqqqqqqqqqvqqqqqqqquqqqqqqqqqqqqqqqqqqqqqwqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                    "qqqqqqqqqqiqqqqqqqqqqqqqquuqqqqqqqqqiqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqgqqqqqqqqquqtqqqqqqqqqqqqwww" +
                    "wvhdwvcmtfnmknafywfwyggggtnwampxdagygyawlvwntwnuctwqhqkqjexdwwmplrpliwddtaxptifgrugiwvcwwuwtgttrbydn" +
                    "wpegakwfwcvqvmtguttqtgtgquwuwedheqqrmfayqqgpdtywpcjwwyvwgvsggyynnjpesogacutjdukxagrpajeypndaxchgwxyo" +
                    "kpwwpqypxqimuwfuuuejesxfwrgawtrfjtgygkaagfnyljdyqrdskxwfauhvattdatyuunlucykwuwunlyiwrlhyosnpwgmanqwl" +
                    "whfufwmahgxqvcaknaawxpdahlkmwqqqiqxrdtwttwyiafgkttwuugaadjreqyypvdjkmtuncgewqiuupamyndwycklyyqaafhhh" +
                    "hhhhhahyyqyywyyyyfoyyyyyyyysyyygyyyyyyyyyyyyysgsssssssssssssfsssgggyyyyyyyyaaaaaaiaiwttttljlllllljlw" +
                    "llllldllllltillllllllelatldllooooxkfffjfrtpguknfjkhpehfffffifffffiffvivvvvvvvvvvvvvvvvvvvvvvvvvttttt" +
                    "ttttttttttyyyyyyyyhhhhhhhhhhhhhhhhhhuhhdhhhhhhhhhhhhhhhhhuhhhhhhhhhhhhhhhhhhhuduyuuduwufuddwttuqwwww" +
                    "ww";

    public static char getFirstLetter(char hanzi) {
        int index = hanzi - HANZI_START;
        if (index >= 0 && index <= HANZI_COUNT) {
            return firstLetterArray.charAt(index);
        }
        else {
            return '#';
        }
    }

    public static String getInitials(String str) {
        StringBuilder sb = new StringBuilder();
        for (int n = 0; n < str.length(); n++) {
            char c = str.charAt(n);
            if (c > 0 && c < 255) {
                sb.append(c);
            }
            else {
                sb.append(getFirstLetter(c));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String str = "测试test一下";
        String pyStr = getInitials(str);
        System.out.println(pyStr);
    }

}
