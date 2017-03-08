#include "com_yip_yipjnidemo_Signature.h"
#include <android/log.h>
#define LOG_TAG "System.out"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

jclass targetClass;
jobject targetObject;
jmethodID targetMethodID;
//官方签名
char *secret="83:80:7C:6C:1D:6C:4C:6D:43:D2:50:0B:86:2C:49:8C:3B:16:5A:DE";
/*
 * Class:     io_github_yanbober_ndkapplication_NdkJniUtils
 * Method:    getCLanguageString
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jint JNICALL Java_com_yip_yipjnidemo_Signature_getCLangString(JNIEnv *env, jobject obj)
{

  jclass clazz=(*env)->GetObjectClass(env,obj);
  if(clazz==0){
    return;
  }
  targetClass=(jclass)(*env)->NewGlobalRef(env,clazz);
  if(targetClass==0){
    return;
  }
  targetObject=(jobject)(*env)->NewGlobalRef(env,obj);
  if(targetObject==0){
    return;
  }
  targetMethodID=(*env)->GetMethodID(env,clazz,"getSignatureInfo","()Ljava/lang/String;");
  if(targetMethodID==0){
    return;
  }
  jstring pring=(jstring)(*env)->CallObjectMethod(env,obj,targetMethodID);
  char *print=(*env)->GetStringUTFChars(env,pring,0);
  if (strcmp(secret, print) == 0){
    return 0;
  }else{
    return 1;
  }
//  return (*env)->NewStringUTF(env,"83:80:7C:6C:1D:6C:4C:6D:43:D2:50:0B:86:2C:49:8C:3B:16:5A:DE");
}