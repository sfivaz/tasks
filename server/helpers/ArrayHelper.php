<?php
/**
 * Created by PhpStorm.
 * User: Fivaz
 * Date: 05/02/2019
 * Time: 07:00
 */

class ArrayHelper
{

    public static function buildCreateData($attributes)
    {
        $data = [];

        foreach ($attributes as $key => $value)
            if ($key != 'listts' && $key != 'cards')
                array_push($data, $value);

        return $data;
    }

    public static function buildUpdateData($attributes)
    {
        $data = [];

        foreach ($attributes as $key => $value)

            if ($key != 'listts' && $key != 'cards')
                array_push($data, $value);

        array_push($data, $attributes["id"]);

        return $data;
    }

    public static function buildDeleteData($attributes)
    {
        $data = [];

        array_push($data, $attributes["id"]);

        return $data;
    }
}