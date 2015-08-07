package com.drivingevaluate.api;
import java.util.HashMap;
import java.util.Map;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.drivingevaluate.config.StateConfig;
import com.drivingevaluate.config.UrlConfig;
import com.drivingevaluate.util.EncryptUtil;
import com.drivingevaluate.util.HttpUtil;
import com.drivingevaluate.util.UrlResolveUtil;
public class JsonResolve {
    public static void sendMessage(Handler handler, Object obj, int what) {
        Message msg = Message.obtain();
        msg.obj = obj;
        msg.what = what;
        handler.sendMessage(msg);
    }
    public static void getMomentById(String id, final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);// 动态的id
        new Thread() {
            public void run() {
                try {
                    String url = UrlResolveUtil.buildFullPath(UrlConfig.bathUrl + UrlConfig.getMomentbyIdUrl, map);
                    String msg = new HttpUtil().getRequest(url);
                    Log.e("Yat3s", "getMomentById----->"+msg);
                    sendMessage(handler, msg, StateConfig.CODE_MOMENT_BY_ID);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }
    public static void addMoment(String locate, String GPS, String authorID, String picturePath, String content, final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        String authorId = EncryptUtil.encode(authorID);
        map.put("authorID", authorId);//发布动态者id
        map.put("locate", locate);//动态所在地区
        map.put("GPS",GPS);//发布动态时的GPS
        map.put("picturePath", picturePath);// 图片路径
        map.put("content",content);// 动态内容
        new Thread() {
            public void run() {
                try {
                    String msg = new HttpUtil().postRequest(UrlConfig.bathUrl + UrlConfig.addMomentUrl, map);
                    sendMessage(handler, msg, StateConfig.CODE_ADD_MOMENT);
                    Log.e("Yat3s", "addMoment----->"+msg);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }
    public static void alterMoment(String locate, String GPS, String id, String picturePath, String content, final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        String Id = EncryptUtil.encode(id);
        map.put("id",Id);//动态id
        map.put("locate", locate);//动态所在地区
        map.put("GPS",GPS);//发布动态时的GPS
        map.put("picturePath", picturePath);// 图片路径
        map.put("content",content);// 动态内容
        new Thread() {
            public void run() {
                try {
                    String msg = new HttpUtil().postRequest(UrlConfig.bathUrl + UrlConfig.alterMomentUrl, map);
                    sendMessage(handler, msg, StateConfig.SUCCESS);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }
    public static void deleteMoment(String id, final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        String Id = EncryptUtil.encode(id);
        map.put("id", Id);// 动态的id
        new Thread() {
            public void run() {
                try {
                    String msg = new HttpUtil().postRequest(UrlConfig.bathUrl + UrlConfig.deleteMomentUrl, map);
                    sendMessage(handler, msg, StateConfig.SUCCESS);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }
    public static void viewMoment(String id,final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        map.put("id",id);//动态id
        new Thread() {
            public void run() {
                try {
                    String msg = new HttpUtil().postRequest(UrlConfig.bathUrl + UrlConfig.viewMomentUrl, map);
                    sendMessage(handler, msg, StateConfig.SUCCESS);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }
    public static void likeMoment(String id, String likeID, final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        String Id = EncryptUtil.encode(id);
        String likeId= EncryptUtil.encode(likeID);
        map.put("id",Id);//动态id
        map.put("likeID",likeId);
        new Thread() {
            public void run() {
                try {
                    String msg = new HttpUtil().postRequest(UrlConfig.bathUrl + UrlConfig.likeMomentUrl, map);
                    sendMessage(handler, msg, StateConfig.SUCCESS);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }
    public static void getMomentByAuthor(String authorID, String pageNum, final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        map.put("authorID", authorID);// 动态作者的id
        map.put("pageNum", pageNum);// 动态id
        new Thread() {
            public void run() {
                try {
                    String url = UrlResolveUtil.buildFullPath(UrlConfig.bathUrl + UrlConfig.getMomentByAuthorUrl, map);
                    String msg = new HttpUtil().getRequest(url);
                    sendMessage(handler, msg, StateConfig.MOMENTARRAY);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }
    public static void getMomentByLocate(String locate,String pageNum,final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        map.put("locate", locate);
        map.put("pageNum", pageNum);
        new Thread() {
            public void run() {
                try {
                    String url = UrlResolveUtil.buildFullPath(UrlConfig.bathUrl + UrlConfig.getMomentByLocateUrl, map);
                    String msg = new HttpUtil().getRequest(url);
                    sendMessage(handler, msg, StateConfig.MOMENTARRAY);
                    Log.e("Yat3s", "getDSchoolListBySort----->"+msg);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }
    public static void addDschool(String sname,String address,String tel,String createdTime,String longitude,
                                  String latitude,String introduction,String photoPath,String marketPrice,String ourPrice,String prePay,final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        map.put("sname", sname);//驾校名
        map.put("address",address);//地区
        map.put("tel", tel);//电话
        map.put("createdTime",createdTime);//创立时间
        map.put("longitude",longitude);//经度
        map.put("latitude",latitude);//玮度
        map.put("introduction",introduction);//介绍
        map.put("photoPath",photoPath);//图片
        map.put("marketPrice",marketPrice);//市场价
        map.put("ourPrice",ourPrice);//我们的价格
        map.put("prePay",prePay);//预付
        new Thread() {
            public void run() {
                try {
                    String msg = new HttpUtil().postRequest(UrlConfig.bathUrl + UrlConfig.addDSchoolUrl, map);
                    sendMessage(handler, msg, StateConfig.SUCCESS);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }
    public static void deleteDSchool(String sid, final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        String id = EncryptUtil.encode(sid);
        map.put("sid", id);//
        new Thread() {
            public void run() {
                try {
                    String msg = new HttpUtil().postRequest(UrlConfig.bathUrl + UrlConfig.deleteDSchoolUrl, map);
                    sendMessage(handler, msg, StateConfig.SUCCESS);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }
    public static void findDSchoolById(String sid, final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        map.put("sid",sid);
        new Thread() {
            public void run() {
                try {
                    String url = UrlResolveUtil.buildFullPath(UrlConfig.bathUrl + UrlConfig.findDSchoolByIdUrl, map);
                    String msg = new HttpUtil().getRequest(url);
                    sendMessage(handler, msg, StateConfig.DSCHOOLOBJECT);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }
    public static void alterDSchool(String sid, String sname,String address, String tel,
                                    String introduction,String photoPath,String schoolId,String marketPrice,String ourPrice,String prePay,final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        String id = EncryptUtil.encode(sid);
        map.put("sid",id);
        map.put("sname", sname);
        map.put("address",address);
        map.put("tel", tel);
        map.put("introduction",introduction);
        map.put("photoPath",photoPath);
        map.put("schoolId",schoolId);
        map.put("marketPrice",marketPrice);
        map.put("ourPrice",ourPrice);
        map.put("prePay",prePay);
        new Thread() {
            public void run() {
                try {
                    String msg = new HttpUtil().postRequest(UrlConfig.bathUrl + UrlConfig.alterDSchoolUrl, map);
                    sendMessage(handler, msg, StateConfig.SUCCESS);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }

    public static void findSchoolByName(String sname,final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        map.put("sname",sname);//驾校名
        new Thread() {
            public void run() {
                try {
                    String url = UrlResolveUtil.buildFullPath(UrlConfig.bathUrl + UrlConfig.findSchoolByNameUrl, map);
                    String msg = new HttpUtil().getRequest(url);
                    sendMessage(handler, msg, StateConfig.DSCHOOLOBJECT);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }
    public static void getDSchoolListBySort(String address, String sort,String offset, final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        map.put("address",address);
        map.put("sort",sort);//获取驾校列表 △注sort=1表示按照评分有高到低排序，sort=2按照价格由低到高排序，其它按照学员数量由高到低排序。
        map.put("offset",offset);
        new Thread() {
            public void run() {
                try {
                    String url = UrlResolveUtil.buildFullPath(UrlConfig.bathUrl + UrlConfig.getDSchoolListBySortUrl, map);
                    String msg = new HttpUtil().getRequest(url);
                    Log.e("Yat3s", "getDSchoolListBySort----->"+msg);
                    sendMessage(handler, msg, StateConfig.DSCHOOLARRAY);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }
    public static void nearByDSchool(String address, String pageNum,String latitude,String longitude,final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();//附近的驾校
        map.put("address", address);
        map.put("pageNum", pageNum);
        map.put("latitude",latitude);
        map.put("longitude",longitude);
        new Thread() {
            public void run() {
                try {
                    String url = UrlResolveUtil.buildFullPath(UrlConfig.bathUrl + UrlConfig.nearByDSchoolUrl, map);
                    String msg = new HttpUtil().getRequest(url);
                    Log.e("Yat3s", "nearByDSchool----->"+msg);
                    sendMessage(handler, msg, StateConfig.DSCHOOLARRAY);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }
    public static void addCoach(String dSchoolId,String name,String introduce,String tel,String photoPath,String classType,final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        map.put("dSchoolId",dSchoolId);//所在驾校Id
        map.put("name",name);//驾校名
        map.put("introduce",introduce);//简介
        map.put("tel",tel);//电话
        map.put("photoPath",photoPath);//图片路径
        map.put("classType",classType);//班级类型
        new Thread() {
            public void run() {
                try {
                    String msg = new HttpUtil().postRequest(UrlConfig.bathUrl + UrlConfig.addCoachUrl, map);
                    sendMessage(handler, msg, StateConfig.SUCCESS);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }
    public static void deleteCoach(String coachId, final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        String Id = EncryptUtil.encode(coachId);
        map.put("coachId", Id);
        new Thread() {
            public void run() {
                try {
                    String msg = new HttpUtil().postRequest(UrlConfig.bathUrl + UrlConfig.deleteCoachUrl, map);
                    sendMessage(handler, msg, StateConfig.SUCCESS);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }
    public static void findCoachById(String coachId, final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        map.put("coachId",coachId);
        new Thread() {
            public void run() {
                try {
                    String url = UrlResolveUtil.buildFullPath(UrlConfig.bathUrl + UrlConfig.findCoachByIdUrl, map);
                    String msg = new HttpUtil().getRequest(url);
                    sendMessage(handler, msg, StateConfig.DSCHOOLOBJECT);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }
    public static void alterCoach(String coachId, String dSchoolId,String introduce, String tel, String photoPath,String classType,final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        String Id = EncryptUtil.encode(coachId);
        map.put("coachId", Id);
        map.put("dSchoolId",dSchoolId);
        map.put("introduce",introduce);
        map.put("tel", tel);
        map.put("photoPath",photoPath);
        map.put("classType",classType);
        new Thread() {
            public void run() {
                try {
                    String msg = new HttpUtil().postRequest(UrlConfig.bathUrl + UrlConfig.alterCoachUrl, map);
                    sendMessage(handler, msg, StateConfig.SUCCESS);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }
    public static void getCoachByDSchool(String dSchoolId,String offset,String sort, final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        map.put("dschoolId",dSchoolId);
        map.put("offset",offset);
        map.put("sort",sort);//分类获得教练，sort=0，新教练，sort=1,高分金牌教练
        new Thread() {
            public void run() {
                try {
                    String url = UrlResolveUtil.buildFullPath(UrlConfig.bathUrl + UrlConfig.getCoachByDSchoolUrl, map);
                    String msg = new HttpUtil().getRequest(url);
                    sendMessage(handler, msg, StateConfig.CODE_GET_COACH_LIST);
                    Log.d("Yat3s", "getCoachByDSchool----->" + msg);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }

    public static void nearByMoment(String locate,String GPS,String pageNum,final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        map.put("locate", locate);
        map.put("pageNum", pageNum);
        map.put("GPS", GPS);
        new Thread() {
            public void run() {
                try {
                    String url = UrlResolveUtil.buildFullPath(UrlConfig.bathUrl + UrlConfig.nearByMomentUrl, map);
                    String msg = new HttpUtil().getRequest(url);
                    sendMessage(handler, msg, StateConfig.MOMENTARRAY);
                    Log.e("Yat3s", "nearByMoment----->"+msg);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }
    public static void addComment(String authorID, String styleID, String information, String picturePath, String style, final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        String authorId = EncryptUtil.encode(authorID);
        String styleId = EncryptUtil.encode(styleID);
        map.put("authorID", authorId);// 评论者id
        map.put("styleID", styleId);// 评论的对象的id,比如 styleID=2 表示评论的是style为2的活动
        map.put("information", information);// 评论内容
        map.put("picturePath", picturePath);// 图片路径
        map.put("style", style);// 评论的对象类型，如果评论的是活动 Style="活动" 如果评论的是是评论
        // Style="评论"
        new Thread() {
            public void run() {
                try {
                    String msg = new HttpUtil().postRequest(UrlConfig.bathUrl + UrlConfig.addCommentUrl, map);
                    sendMessage(handler, msg, StateConfig.CODE_ADD_COMMENT);
                    Log.e("Yat3s", "addComment----->"+msg);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }

    public static void deleteComment(String id, final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        String Id = EncryptUtil.encode(id);
        map.put("id", Id);// 评论的id
        new Thread() {
            public void run() {
                try {
//					String msg = new HttpUtil().postRequest(UrlConfig.bathUrl + UrlConfig.deleteCommentUrl, map);
//					sendMessage(handler, msg, StateConfig.SUCCESS);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }

    public static void getCommentById(String id, final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);// 评论的id
        new Thread() {
            public void run() {
                try {
//					String url = UrlResolveUtil.buildFullPath(UrlConfig.bathUrl + UrlConfig.getCommentbyIdUrl, map);
//					String msg = new HttpUtil().getRequest(url);
//					sendMessage(handler, msg, StateConfig.COMMENTOBJECT);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }

    public static void getCommentByAuthor(String authorID, String pageNum, final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        map.put("authorID", authorID);// 评论作者的id
        map.put("pageNum", pageNum);// 评论id
        new Thread() {
            public void run() {
                try {
//					String url = UrlResolveUtil.buildFullPath(UrlConfig.bathUrl + UrlConfig.getCommentByAuthor, map);
//					String msg = new HttpUtil().getRequest(url);
//					sendMessage(handler, msg, StateConfig.COMMENTARRAY);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }

    public static void getCommentByStyle(String styleID, String style,String pageNum,final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        map.put("styleID", styleID);// 评论对象的id
        map.put("style", style);// 评论对象的类型
        map.put("pageNum",pageNum);
        new Thread() {
            public void run() {
                try {
                    String url=UrlResolveUtil.buildFullPath(UrlConfig.bathUrl + UrlConfig.getCommentByStyle,map);
                    String msg = new HttpUtil().getRequest(url);
                    Log.e("Yat3s", "getCommentByStyle----->"+msg);
                    sendMessage(handler, msg,  StateConfig.CODE_COMMENT_BY_STYLE);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }
    public static void updateServerVer(String version, String apkUrl,String remark,final Handler handler) {
        final Map<String, String> map = new HashMap<String, String>();
        map.put("version",version);
        map.put("apkUrl",apkUrl);
        map.put("remark",remark);
        new Thread() {
            public void run() {
                try {
                    String msg = new HttpUtil().postRequest(UrlConfig.bathUrl + UrlConfig.updateServerVer, map);
                    sendMessage(handler, msg, StateConfig.SUCCESS);

                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }
    public static void getServerVer(final Handler handler) {
        new Thread() {
            public void run() {
                try {
                    String url =UrlConfig.bathUrl + UrlConfig.getServerVer;
                    String msg = new HttpUtil().getRequest(url);
                    Log.e("Yat3s", "getServerVer----->"+msg);
                    sendMessage(handler, msg, StateConfig.CODE_GET_VERCODE);
                } catch (Exception e) {
                    sendMessage(handler, e.toString(), StateConfig.REQUESTERROR);
                }
            }
        }.start();
    }
}
