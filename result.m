saveToFile = 1;

n_arr_index = [6:1:11];
n_arr = pow2(n_arr_index); %64,128,...,2048

s_arr_index = [2:1:9];
s_arr = pow2(s_arr_index); %4,8,...,512

%discrepancy_n_MyCaching = [0.00112525 0.00133855 0.00051299 0.00019039 0.00002170 0.00000756 ];
%discrepancy_n_CentralizedCaching = [ 0.00007243 0.00002559 0.00000602 0.00000233 0.00000185 0.00000112 ];
%discrepancy_n_RandomCaching = [0.00765596 0.00648161 0.00591624 0.00715188 0.00676342 0.00695077 ];
%dicrepancy_n_SlidingWindow = [0.00686025 0.00415735 0.00329228 0.00245911 0.00090989 0.00035232];
discrepancy_n_MyCaching = [1.94934324 1.30034890 1.16283332 1.05030890 1.02015693 1.00505280 ];
discrepancy_n_CentralizedCaching = [1.39419033 1.20810348 1.08537836 1.04829810 1.03248295 1.01695637 ];
discrepancy_n_RandomCaching = [8.65744036 9.97499433 8.82265 9.49413265 8.56845781 8.57810918];
dicrepancy_n_SlidingWindow = [3.49785606 2.42293235 2.05243202 1.81297153 1.90085380 1.73269552 ];


segmentDistance_n_MyCaching = [400.40149972 380.20450739 341.78745561 315.44009568 289.36539197 251.31073070]./4;
segmentDistance_n_CentralizedCaching = [351.46033319 360.28744783 342.74842554 309.99739972 277.09141125 239.23381147 ]./4;
segmentDistance_n_RandomCaching = [504.90436700 448.12499712 440.46076008 427.02374445 356.91385604 333.14408034 ]./4;
segmentDistance_n_SlidingWindow = [443.13755878 401.91978379 385.06044526 359.38432129 306.04514652 270.26147525 ]./4;

discrepancy_s_MyCaching = [0.00061191 0.00000244 0.00000360 0.00000357 0.00000220 0.00000195 0.00000155 0.00000184 ];
discrepancy_s_CentralizedCaching = [0.00043297 0.00000952 0.00000170 0.00000183 0.00000460 0.00003825 0.00017539 0.00024038 ];
discrepancy_s_RandomCaching = [0.02182822 0.02952885 0.02014666 0.01232904 0.00923375 0.00688409 0.00477005 0.00368793 ];
dicrepancy_s_SlidingWindow = [0.00169762 0.00266579 0.00252239 0.00277177 0.00336645 0.00292365 0.00122794 0.00076258 ];
segmentDistance_s_MyCaching = [110.68686869 143.19569459 198.45088486 237.48205640 252.14313518 278.13848546 311.04477751 353.55706180 ]./4;
segmentDistance_s_CentralizedCaching = [117.86678082 151.78144756 185.16866961 215.86334044 250.33142480 289.37385494 316.47984220 332.58794767 ]./4;
segmentDistance_s_RandomCaching = [125.52962687 178.10941627 224.32466491 266.86830214 328.66639956 386.00890525 416.63041968 502.85153332 ]./4;
segmentDistance_s_SlidingWindow = [111.52428044 163.49050362 207.10722703 244.05368817 277.02366112 319.40238732 339.95529923 375.47171242 ]./4;

segmentDistance_n_MySearch = [424.42200907 373.04119642 345.87811002 326.55113042 299.05704361 244.60045662 ]./4;
segmentDistance_n_CentralizedSearch = [379.82516038 369.92297620 337.71161143  313.97241559 281.53177782 242.94022511 ]./4;
segmentDistance_n_DHT = [444.17016978 402.57997251 393.98368733 381.44772739 378.07452433 364.63468723 ]./4;
segmentDistance_n_Flooding = [461.31198826 397.91580251 388.68940965 389.67905602 377.54748838 383.29620166 ]./4;
searchLatency_n_MySearch = [142.18481696 99.98650422 64.75383473 3.70016635 2.48528051 0.64511699 ]./4;
%searchLatency_n_CentralizedSearch = [427.88571429 471.93129771 501.50699301 511.31675875 425.80605487 555.53849577 ]./4;
searchLatency_n_CentralizedSearch=[452.59777060 367.34562386 372.48943900 331.41378922 297.61938358 249.35670917]./4;
%searchLatency_n_DHT = [904.69740492 1217.02262113 1338.65853017 1549.11211504 1824.02357272 1773.90628061 ]./4;
searchLatency_n_DHT = [969.03096415 1445.11476182 1439.05718877 1644.56756070 1650.44651275 1396.49612464]./4;
%searchLatency_n_Flooding = [2262.93577288 2182.67401241 2441.10495978
%2738.10257757 2843.61738790 2979.22518069 ]./4;
%searchLatency_n_Flooding = [2141.90451435 2173.88186970 2399.98993415 2339.63406773 2484.02225129 2305.41226702 ]./4;
searchLatency_n_Flooding = [3704.11194591 3666.05755819 3533 3331.63038040 3199.20774428 3202.76551788]./4;
hitRate_n_MySearch = [0.23906250 0.34688895 0.54581368 0.97498292 0.98506478 0.99863542 ];
hitRate_n_CentralizedSearch = [1.00000000 1.00000000 1.00000000 1.00000000 1.00000000 1.00000000 ];
hitRate_n_DHT = [1.00000000 1.00000000 1.00000000 1.00000000 1.00000000 1.00000000 ];
hitRate_n_Flooding = [0.13281250 0.26356250 0.41946445 0.65086806 0.86219215 0.83312665 ];

segmentDistance_s_MySearch = [139.55996296 177.13233456 211.08938719 248.17042885 277.30099077 305.10253696 346.19309222 364.73898651 ]./4;
segmentDistance_s_CentralizedSearch = [137.78250936 175.15402640 206.62442479 235.94682252  275.96233836 300.47635392 328.76671538 363.57162501 ]./4;
segmentDistance_s_DHT = [309.32276351 334.20915653 343.03861217 338.24907849 359.82229642 377.53324720 395.64150120 405.48929107 ]./4;
segmentDistance_s_Flooding = [309.20043088 331.53412665 339.38625561 350.11587642 356.79879857 396.04032771 395.38158183 406.02348098 ]./4;
searchLatency_s_MySearch = [0.52340824 1.04467062 0.39581351 0.00000000 0.00000000 4.91467902 13.12410735 38.67789696 ]./4;
searchLatency_s_CentralizedSearch = [544.26481481 519.35224154 447.61126500 509.26746324 553.77734375 511.10242588 467.00090580 541.84215501 ]./4;
searchLatency_s_DHT = [492.44832595 741.83565823 1040.09064955 1288.39406629 1627.70013938 1750.77317086 1847.05015875 2091.80507149 ]./4;
searchLatency_s_Flooding = [2592.16114901 2776.37501770 2856.85984677 2918.01128372 2865.52797808 2903.69092016 2827.38707225 2795.58952141 ]./4;
%hitRate_s_MySearch = [0.99906367 0.99811321 0.99904853 1.00000000 0.99995565 0.96429382 0.93188337 0.62142698 ];
hitRate_s_MySearch = [0.99906367 0.99811321 0.99904853 1.00000000 0.99995565 0.99804779 0.99276599 0.96777384];
hitRate_s_CentralizedSearch = [1.00000000 1.00000000 1.00000000 1.00000000 1.00000000 1.00000000 1.00000000 1.00000000 ];
hitRate_s_DHT = [1.00000000 1.00000000 1.00000000 1.00000000 1.00000000 1.00000000 1.00000000 0.98402138 ];
%hitRate_s_Flooding = [0.99910233 0.99905482 0.99890452 0.99842995 0.96420592 0.82045651 0.76449296 0.42244884 ];
%hitRate_s_Flooding = [1 1 0.99935722 0.96013880 0.91586920 0.82233043
%0.69142444 0.58970584 ];\
hitRate_s_Flooding = [1 1 1 0.99731769 0.99416352 0.92876128 0.84301268 0.71167924];

normW = [0 5 10 15 20 25];
segmentDistance_normW_raw = [350.96783912 328.65079052 308.21654476 303.95304009 303.80258035 297.94744957]./4;
segmentDistance_normW = segmentDistance_normW_raw./segmentDistance_normW_raw(1);

epsilon_label = [0.1 0.01 0.001];
epsilon = [1 2 3];
discrepancy_epsilon = [0.00638719 0.00000195 0.00000172];

%numIteration = [0:1:11];
%discrepancy_numIteration = [0.0136279 0.00082020 0.00064291 0.00058920 0.00055251 0.00051958 0.00048254 0.00045948 0.00043686 0.00041256 0.00039582 0.00039582]./2;
%segmentDistance_numIteration = [427.02374445 330.44275680 315.44009568 313.83657965 312.41501886 311.74746827 311.52237377 311.39506952 310.42285097 310.57555481 310.14927821 310.14927821]./4;
numIteration = [0:1:6];
%discrepancy_numIteration = [0.0136279 0.00082020 0.00064291 0.00058920 0.00055251 0.00051958 0.00048254]./2;
discrepancy_numIteration = [8.56845781 1.12148344 1.03909717 1.03293911 1.03067879 1.03035116  1.02866741];
segmentDistance_numIteration = [427.02374445 330.44275680 315.44009568 313.83657965 312.41501886 311.74746827 311.52237377]./4;
numNeighbor=[2:1:10];
hitRate_numNeighbor=[0.43 0.98 0.99105556 0.99970372 0.99983128 0.999 0.9993 0.99210157 0.99199623];
numNeighbor_analysis=[2 2.25 2.5 2.75 2.875 3 3.25 3.5 4 5 6 7 8 9 10];
hitRate_numNeighbor_analysis=[0.4828 0.65 0.8 0.9 0.945 0.9618 0.97 0.975 0.9776 0.9833 0.9863 0.9883 0.9896 0.9906 0.9914];
period=[20:20:120];
hitRate_period=[0.99890639 0.99 0.98 0.96918114 0.9531 0.9312];
period_analysis=[10:10:120];
hitRate_period_analysis=[ 0.9933 0.9869 0.9806 0.9743 0.9680 0.9618 0.9557 0.9496 0.9435 0.9375 0.9315 0.9256];

capacityParam=[0.1 0.2 0.3 0.4 0.5 0.6 0.7];
hitRate_capacityParam=[0.51578880 0.81034801 0.96522108 0.97578276 0.99806977 0.99814054 0.99998604];
discrepancy_capacityParam=[0.00000805 0.00000636 0.00000191 0.00000169 0.00000206 0.00000154 0.00000109];
segmentDistance_capacityParam=[380.48371118 339.43977600 310.38548265 285.16767865 255.30604190 219.07976332 194.74743809]./4;
searchLatency_capacityParam=[63.77761708 23.45159117 4.69984671 3.29979516 1.12685685 0.99136837 0.00000000];

control_overhead_s_TABEX_sim=[0.0116 0.0229 0.0455 0.0903 0.1796 0.3427 0.5104 0.5811].*8;
control_overhead_s_TABEX_sim = control_overhead_s_TABEX_sim.*5;
control_overhead_s_TABEX_sim =control_overhead_s_TABEX_sim./9; 
%control_overhead_s_DHT_sim=[0.0069 0.0097 0.0119 0.0126 0.0127 0.0128 0.0129 0.013]
%control_overhead_s_DHT_sim=[0.0087 0.0085 0.0113 0.0141 0.0168 0.01874 0.0208 0.02329];
control_overhead_s_DHT_sim=[0.011 0.017 0.0226 0.0282 0.0336 0.0374 0.0416 0.0465].*8;

seg_dist_numEx=[490.85271295 459.39329180 432.06680429 405.75994043 347.70681241 310.10962518 291.58691889 291.06071154 288.62719319 288.51147925 286.82406242  285.90869291 283.28095457  282.07671273 280.16707521 ]./4;
numEx=[1:1:15];

%plot graph



% %--------------
% % Set 1
% %---------------
%
h=figure(1);
plot(n_arr_index,discrepancy_n_RandomCaching,'-.^',n_arr_index,dicrepancy_n_SlidingWindow,'--o',n_arr_index,discrepancy_n_MyCaching,'-s',n_arr_index,discrepancy_n_CentralizedCaching,'--vm','LineWidth', 1, 'MarkerSize', 8);
xlabel('average number of peers','fontsize',14)
ylabel('peer load','fontsize',14)
%legend('Random-TableX','Sliding-TableX','POPCA-TableX','Centralized-TableX','Location','Best');
legend('Random','Sliding','POPCA','Centralized','Location','Best');
set(gca,'XTickMode','manual','XTick', n_arr_index) ;
set(gca,'XTickLabel',n_arr,'fontsize',14);
axis([6, 11, 1,12]);
if (saveToFile == 1)
    saveas(h,'discrepancy_numPeer_caching.eps', 'psc2')
    close(h);
end
% 
h=figure(2);
plot(n_arr_index,segmentDistance_n_RandomCaching,'-.^',n_arr_index,segmentDistance_n_SlidingWindow,'--o',n_arr_index,segmentDistance_n_MyCaching,'-s',n_arr_index,segmentDistance_n_CentralizedCaching,'--vm', 'LineWidth', 1, 'MarkerSize', 8);
xlabel('average number of peers','fontsize',14)
ylabel('segment distance (ms)','fontsize',14)
%legend('Random-TableX','Sliding-TableX','POPCA-TableX','Centralized-TableX
%','Location','Best');
legend('Random','Sliding','POPCA','Centralized','Location','Best');
set(gca,'XTickMode','manual','XTick', n_arr_index) ;
set(gca,'XTickLabel', n_arr,'fontsize',14);
if (saveToFile == 1)
    saveas(h,'segmentDistance_numPeer_caching.eps', 'psc2')
    close(h);
end
% 
% %--------------
% % Set 2
% %---------------
% 
% h=figure(3);
% plot(s_arr_index,discrepancy_s_RandomCaching,'-.^',s_arr_index,dicrepancy_s_SlidingWindow,'--o',s_arr_index,discrepancy_s_MyCaching,'-s',s_arr_index,discrepancy_s_CentralizedCaching,'--v','LineWidth', 1, 'MarkerSize', 8);
% xlabel('number of segments','fontsize',14)
% ylabel('popularity-supply discrepancy','fontsize',14)
% legend('Random-TableX','Sliding-TableX','POPCA-TableX','Centralized-TableX','Location','Best');
% set(gca,'XTickMode','manual','XTick', s_arr_index) ;
% set(gca,'XTickLabel', s_arr,'fontsize',14);
% axis([2, 9, 0, 0.00924]);
% if (saveToFile == 1)
%     saveas(h,'discrepancy_numSeg_caching.eps', 'psc2')
%     close(h);
% end
% 
% h=figure(4);
% plot(s_arr_index,segmentDistance_s_RandomCaching,'-.^',s_arr_index,segmentDistance_s_SlidingWindow,'--o',s_arr_index,segmentDistance_s_MyCaching,'-s',s_arr_index,segmentDistance_s_CentralizedCaching,'--v', 'LineWidth', 1, 'MarkerSize', 8);
% xlabel('number of segments','fontsize',14)
% ylabel('segment distance (ms)','fontsize',14)
% legend('Random-TableX','Sliding-TableX','POPCA-TableX','Centralized-TableX','Location','Best');
% set(gca,'XTickMode','manual','XTick', s_arr_index) ;
% set(gca,'XTickLabel', s_arr,'fontsize',14);
% if (saveToFile == 1)
%     saveas(h,'segmentDistance_numSeg_caching.eps', 'psc2')
%     close(h);
% end
% 
% %--------------
% % Set 3
% %---------------
% 
% h=figure(5);
% plot(n_arr_index,segmentDistance_n_Flooding,'-.^',n_arr_index,segmentDistance_n_DHT,'--o',n_arr_index,segmentDistance_n_MySearch,'-s',n_arr_index,segmentDistance_n_CentralizedSearch,'--v', 'LineWidth', 1, 'MarkerSize', 8);
% xlabel('average number of peers','fontsize',14)
% ylabel('segment distance (ms)','fontsize',14)
% legend('POPCA-Flooding','POPCA-DHT','POPCA-TableX','POPCA-Centralized','Location','Best');
% set(gca,'XTickMode','manual','XTick', n_arr_index) ;
% set(gca,'XTickLabel', n_arr,'fontsize',14);
% if (saveToFile == 1)
%     saveas(h,'segmentDistance_numPeer_search.eps', 'psc2')
%     close(h);
% end
% 
h=figure(6);
plot(n_arr_index,searchLatency_n_Flooding,'-.^',n_arr_index,searchLatency_n_DHT,'--o',n_arr_index,searchLatency_n_CentralizedSearch,'-.vm',n_arr_index,searchLatency_n_MySearch,'-s','LineWidth', 1, 'MarkerSize', 8);
xlabel('average number of peers','fontsize',14)
ylabel('search latency (ms)','fontsize',14)
legend('Biased random walk','DHT-PNS','Client-server','POPCA','Location','Best');
set(gca,'XTickMode','manual','XTick', n_arr_index) ;
set(gca,'XTickLabel', n_arr,'fontsize',14);
axis([6, 11, 0, 1200]);
if (saveToFile == 1)
    saveas(h,'searchLatency_numPeer_search.eps', 'psc2')
    close(h);
end
% 
% h=figure(7);
% plot(n_arr_index,hitRate_n_MySearch,'-s', 'LineWidth', 1, 'MarkerSize', 8);
% xlabel('average number of peers','fontsize',14)
% ylabel('hit rate','fontsize',14)
% %legend('POPCA-Flooding','POPCA-DHT','POPCA-TableX','POPCA-Centralized','Location','Best');
% set(gca,'XTickMode','manual','XTick', n_arr_index) ;
% set(gca,'XTickLabel', n_arr,'fontsize',14);
% %axis([6, 11, 0.7268, 1]);
% if (saveToFile == 1)
%     saveas(h,'hitRate_numPeer_search.eps', 'psc2')
%     close(h);
% end
% 
% %--------------
% % Set 4
% %---------------
% h=figure(8);
% plot(s_arr_index,segmentDistance_s_Flooding,'-.^',s_arr_index,segmentDistance_s_DHT,'--o',s_arr_index,segmentDistance_s_MySearch,'-s',s_arr_index,segmentDistance_s_CentralizedSearch,'--v', 'LineWidth', 1, 'MarkerSize', 8);
% xlabel('number of segments','fontsize',14)
% ylabel('segment distance (ms)','fontsize',14)
% legend('POPCA-Flooding','POPCA-DHT','POPCA-TableX','POPCA-Centralized','Location','Best');
% set(gca,'XTickMode','manual','XTick', s_arr_index) ;
% set(gca,'XTickLabel', s_arr,'fontsize',14);
% if (saveToFile == 1)
%     saveas(h,'segmentDistance_numSeg_search.eps', 'psc2')
%     close(h);
% end
% 
% h=figure(9);
% plot(s_arr_index,searchLatency_s_Flooding,'-.^',s_arr_index,searchLatency_s_DHT,'--o',s_arr_index,searchLatency_s_MySearch,'-s','LineWidth', 1, 'MarkerSize', 8);
% xlabel('number of segments','fontsize',14)
% ylabel('search latency (ms)','fontsize',14)
% legend('POPCA-Flooding','POPCA-DHT','POPCA-TableX','Location','Best');
% set(gca,'XTickMode','manual','XTick', s_arr_index) ;
% set(gca,'XTickLabel', s_arr,'fontsize',14);
% %axis([2, 9, -10, 650]);
% if (saveToFile == 1)
%     saveas(h,'searchLatency_numSeg_search.eps', 'psc2')
%     close(h);
% end
% 
h=figure(10);
plot(s_arr_index,hitRate_s_DHT,'--o',s_arr_index,hitRate_s_MySearch,'-s',s_arr_index,hitRate_s_Flooding,'-.^','LineWidth', 1, 'MarkerSize', 8);
xlabel('number of segments','fontsize',14)
ylabel('hit rate','fontsize',14)
legend('DHT-PNS','POPCA','Biased random walk','Location','Best');
set(gca,'XTickMode','manual','XTick', s_arr_index) ;
set(gca,'XTickLabel', s_arr,'fontsize',14);
axis([2, 9, 0.7, 1]);
if (saveToFile == 1)
    saveas(h,'hitRate_numSeg_search.eps', 'psc2')
    close(h);
end
% 
% %----------------------------------
% h=figure(11);
% plot(normW, segmentDistance_normW, '-s', 'LineWidth', 1, 'MarkerSize', 8);
% xlabel('number of peers in subset W','fontsize',14)
% ylabel('normalized segment distance','fontsize',14)
% set(gca,'XTickMode','manual','XTick', normW, 'fontsize',14) ;
% axis([0, 25, 0.84, 1]);
% if (saveToFile == 1)
%     saveas(h,'segmentDistance_normW.eps', 'psc2')
%     close(h);
% end
% 
% h=figure(12);
% plot(epsilon, discrepancy_epsilon, '-s', 'LineWidth', 1, 'MarkerSize', 8);
% xlabel('\epsilon','fontsize',14)
% ylabel('popularity-supply discrepancy','fontsize',14)
% set(gca,'XTickMode','manual','XTick', epsilon) ;
% set(gca,'XTickLabel', epsilon_label,'fontsize',14);
% axis([1, 3, 0, 0.003]);
% if (saveToFile == 1)
%     saveas(h,'discrepancy_epsilon.eps', 'psc2')
%     close(h);
% end
% 
% h=figure(13);
% plot(numIteration, discrepancy_numIteration, '-s', 'LineWidth', 1, 'MarkerSize', 8);
% xlabel('number of iterations','fontsize',14)
% ylabel('popularity-supply discrepancy','fontsize',14)
% set(gca,'XTickMode','manual','XTick', numIteration, 'fontsize',14);
% axis([0, 6, 0, 0.007]);
% if (saveToFile == 1)
%     saveas(h,'discrepancy_numIteration.eps', 'psc2')
%     close(h);
% end
% 
% h=figure(14);
% plot(numIteration, segmentDistance_numIteration, '-s', 'LineWidth', 1, 'MarkerSize', 8);
% xlabel('number of iterations','fontsize',14)
% ylabel('segment distance (ms)','fontsize',14)
% set(gca,'XTickMode','manual','XTick', numIteration, 'fontsize',14);
% axis([0, 10, 76, 110]);
% if (saveToFile == 1)
%     saveas(h,'segmentDistance_numIteration.eps', 'psc2')
%     close(h);
% end
% 
% 
% %-------------
% 
% %Analysis VS Simulation
% 
h=figure(14);
plot(numNeighbor, hitRate_numNeighbor, 'x', numNeighbor_analysis, hitRate_numNeighbor_analysis, '-', 'LineWidth', 1, 'MarkerSize', 8);
xlabel('number of neighbors','fontsize',14)
ylabel('hit rate','fontsize',14)
legend('Simulation','Analysis','Location','Best');
set(gca,'fontsize',14)
if (saveToFile == 1)
    saveas(h,'hitRate_numNeighbor.eps', 'psc2')
    close(h);
end

h=figure(15);
plot(period, hitRate_period, 'x', period_analysis, hitRate_period_analysis, '-', 'LineWidth', 1, 'MarkerSize', 8);
xlabel('segment table advertising period (seconds)','fontsize',14)
ylabel('hit rate','fontsize',14)
legend('Simulation','Analysis','Location','Best');
axis([10, 120, 0.4, 1]);
set(gca, 'fontsize',14)
if (saveToFile == 1)
    saveas(h,'hitRate_period.eps', 'psc2')
    close(h);
end


h=figure(16);
s_arr=[2 3 4 5 6 7 8 9];
s_pow2_arr=pow2(s_arr);
%plot(s_arr,control_overhead_s_TABEX_sim,'-s',s_arr,control_overhead_s_DHT_sim,'-o');
plot(s_arr,control_overhead_s_TABEX_sim,'-s', s_arr,control_overhead_s_DHT_sim,'--o', 'LineWidth', 1, 'MarkerSize', 8);
xlabel('number of segments','fontsize',14);
ylabel('control overhead (kbps)','fontsize',14);
legend('POPCA','DHT-PNS','Location','NorthWest')
set(gca,'XTickMode','manual','XTick',s_arr) ;
set(gca,'XTickLabel',s_pow2_arr,'fontsize',14);
if (saveToFile == 1)
   saveas(h,'ctrl_overhead_seg.eps', 'psc2')
   close(h);
end 

h=figure(17);
%[AX,H1,H2]=plotyy( numIteration, discrepancy_numIteration.*(10^3),numIteration, segmentDistance_numIteration, 'plot');
[AX,H1,H2]=plotyy( numIteration, discrepancy_numIteration,numIteration, segmentDistance_numIteration, 'plot');
set(get(AX(1),'Ylabel'),'String','peer load','fontsize',14,'color','k')
set(get(AX(2),'Ylabel'),'String','segment distance (ms)','fontsize',14,'color','k')
xlabel('number of segment replacements','fontsize',14)
set(AX(1),'YColor','k','fontsize',14)
set(AX(2),'YColor','k','fontsize',14)
set(H1,'LineStyle','-', 'LineWidth', 1, 'Marker','s','MarkerSize', 8)
set(H2,'LineStyle','--', 'LineWidth', 1, 'Marker','o','MarkerSize', 8)
legend([H2;H1],'Segment distance','Peer load','Location','Best');
set(AX(1),'YTickMode','manual') ;
set(AX(1),'YTick',[0 2 4 6 8 10 12 14 16 18 20])
set(AX(2), 'XTick',[])
%set(AX(2),'YTickMode','manual') ;
%set(AX(2),'YTick',[70 80 90 100 110 120])
%axis auto;
axis([0, 6, 0, 20]);

if (saveToFile == 1)
    saveas(h,'discrepancy_segDist_numIteration.eps', 'psc2')
    close(h);
end

h=figure(18);
plot(numEx,seg_dist_numEx,'-x', 'LineWidth', 1, 'MarkerSize', 8);
xlabel('number of advertisements','fontsize',14);
ylabel('segment distance (ms)','fontsize',14);
%legend('TableX','DHT-PNS','Location','NorthWest')
%set(gca,'XTickMode','manual','XTick',s_arr) ;
axis([1, 15, 70, 130]);
set(gca,'fontsize',14);
if (saveToFile == 1)
   saveas(h,'segDist_numEx.eps', 'psc2')
   close(h);
end 