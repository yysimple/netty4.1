package com.simple.util;

import com.simple.constant.Constants;
import com.simple.entity.FileBurstData;
import com.simple.entity.FileBurstInstruct;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-17 15:42
 **/
public class FileUtil {

    private static final Integer FILE_SIZE = 1000 * 1024;

    /**
     * 读取文件操作
     *
     * @param fileUrl
     * @param readPosition
     * @return
     * @throws IOException
     */
    public static FileBurstData readFile(String fileUrl, Integer readPosition) throws IOException {
        // 拿到文件信息
        File file = new File(fileUrl);
        //r: 只读模式 rw:读写模式
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        // 定位到文件指定字节处开始读取
        randomAccessFile.seek(readPosition);
        // 定义缓存区大小
        byte[] bytes = new byte[FILE_SIZE];
        // 开始读取文件
        int readSize = randomAccessFile.read(bytes);
        // 文件数据不存在的情况，关闭随机文件流，并返回读取完毕状态
        if (readSize <= 0) {
            randomAccessFile.close();
            // Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
            return new FileBurstData(Constants.FileStatus.COMPLETE);
        }
        // 文件分片操作
        FileBurstData fileInfo = new FileBurstData();
        // 设置文件的基本信息
        fileInfo.setFileUrl(fileUrl);
        fileInfo.setFileName(file.getName());
        // 开始字节处
        fileInfo.setBeginPos(readPosition);
        // 结束字节处
        fileInfo.setEndPos(readPosition + readSize);
        // 不足1024需要拷贝去掉空字节
        if (readSize < FILE_SIZE) {
            byte[] copy = new byte[readSize];
            System.arraycopy(bytes, 0, copy, 0, readSize);
            fileInfo.setBytes(copy);
            // 读取此次文件结尾处
            fileInfo.setStatus(Constants.FileStatus.END);
        } else {
            // 如果数据满足分片要求的话，还存在数据可读，所以只是读取到了文件的中间
            fileInfo.setBytes(bytes);
            fileInfo.setStatus(Constants.FileStatus.CENTER);
        }
        randomAccessFile.close();
        return fileInfo;
    }

    public static FileBurstInstruct writeFile(String baseUrl, FileBurstData fileBurstData) throws IOException {
        /**
         * 文件读取完成的状态下，返回完成结果
         */
        if (Constants.FileStatus.COMPLETE == fileBurstData.getStatus()) {
            //Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
            return new FileBurstInstruct(Constants.FileStatus.COMPLETE);
        }

        // 这里只是从文件切片中写出该切片数据
        File file = new File(baseUrl + File.separator + fileBurstData.getFileName());
        // r: 只读模式 rw:读写模式
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        // 移动文件记录指针的位置，对应切片的起始处
        randomAccessFile.seek(fileBurstData.getBeginPos());
        // 调用了seek（start）方法，是指把文件的记录指针定位到start字节的位置。也就是说程序将从start字节开始写数据
        randomAccessFile.write(fileBurstData.getBytes());
        // 关闭文件流
        randomAccessFile.close();

        // 上次如果是标记的是END，表示已经读取到了尾部，此时需要把文件写出状态也更新成完成状态
        if (Constants.FileStatus.END == fileBurstData.getStatus()) {
            // Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
            return new FileBurstInstruct(Constants.FileStatus.COMPLETE);
        }

        // 修改文件分片传输指令，下次读取的位置等信息；
        FileBurstInstruct fileBurstInstruct = new FileBurstInstruct();
        // Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
        fileBurstInstruct.setStatus(Constants.FileStatus.CENTER);
        // 客户端文件URL
        fileBurstInstruct.setClientFileUrl(fileBurstData.getFileUrl());
        // 读取位置
        fileBurstInstruct.setReadPosition(fileBurstData.getEndPos() + 1);

        return fileBurstInstruct;
    }
}
